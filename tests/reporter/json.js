"use strict";
/**
 * @module JSON
 */
/**
 * Module dependencies.
 */

var Base = require("./base");
var fs = require("fs");
var EVENT_TEST_PASS = "pass";
var EVENT_TEST_FAIL = "fail";
var EVENT_TEST_END = "test end";
var EVENT_RUN_END = "end";
var EVENT_TEST_PENDING = "pending";

/**
 * Expose `JSON`.
 */

exports = module.exports = JSONReporter;

/**
 * Constructs a new `JSON` reporter instance.
 *
 * @public
 * @class JSON
 * @memberof Mocha.reporters
 * @extends Mocha.reporters.Base
 * @param {Runner} runner - Instance triggers reporter actions.
 * @param {Object} [options] - runner options
 */
function JSONReporter(runner, options) {
    Base.call(this, runner, options);

    var self = this;
    var tests = [];
    var pending = [];
    var failures = [];
    var passes = [];

    runner.on(EVENT_TEST_END, function (test) {
        tests.push(test);
    });

    runner.on(EVENT_TEST_PASS, function (test) {
        passes.push(test);
    });

    runner.on(EVENT_TEST_FAIL, function (test) {
        failures.push(test);
    });

    runner.on(EVENT_TEST_PENDING, function (test) {
        pending.push(test);
    });

    runner.once(EVENT_RUN_END, function () {
        var obj = {
            stats: self.stats,
            tests: tests.map(clean),
            pending: pending.map(clean),
            failures: failures.map(clean),
            passes: passes.map(clean),
        };

        runner.testResults = obj;
        if (tests.length !== 0) {
            var fileName = tests[0].parent.parent.file.split("/").pop();
        } else if (failures.length !== 0) {
            var fileName = failures[0].parent.parent.file.split("/").pop();
        } else {
            console.log("Failed to write to report");
            return;
        }
        var reportsPath = options.reporterOptions.reportsPath
        if (!fs.existsSync(reportsPath)){
            fs.mkdirSync(reportsPath);
        }
        fs.writeFile(
            reportsPath + fileName + ".json",
            JSON.stringify(obj, null, 2),
            "utf8",
            function (err) {
                if (err) {
                    console.log(
                        "An error occured while writing JSON Object to File."
                    );
                    return console.log(err);
                }

                console.log(reportsPath + fileName + ".json" + " file has been saved.");
            }
        );
    });
}

/**
 * Return a plain-object representation of `test`
 * free of cyclic properties etc.
 *
 * @private
 * @param {Object} test
 * @return {Object}
 */
function clean(test) {
    var err = test.err || {};
    if (err instanceof Error) {
        err = errorJSON(err);
    }

    return {
        suite: test.parent.title,
        title: test.title,
        fullTitle: test.fullTitle(),
        file: test.parent.parent.file,
        body: test.body,
        duration: test.duration,
        currentRetry: test.currentRetry(),
        err: cleanCycles(err),
    };
}

/**
 * Replaces any circular references inside `obj` with '[object Object]'
 *
 * @private
 * @param {Object} obj
 * @return {Object}
 */
function cleanCycles(obj) {
    var cache = [];
    return JSON.parse(
        JSON.stringify(obj, function (key, value) {
            if (typeof value === "object" && value !== null) {
                if (cache.indexOf(value) !== -1) {
                    // Instead of going in a circle, we'll print [object Object]
                    return String(value);
                }

                cache.push(value);
            }

            return value;
        })
    );
}

/**
 * Transform an Error object into a JSON object.
 *
 * @private
 * @param {Error} err
 * @return {Object}
 */
function errorJSON(err) {
    var res = {};
    Object.getOwnPropertyNames(err).forEach(function (key) {
        res[key] = err[key];
    }, err);
    return res;
}

JSONReporter.description = "single JSON object";