---
name: Tech Day
about: Create a tech day ticket
title: ''
type: Task
labels: ['Tech Day', 'Area:Tech']

---

The goal of this issue is to work on technical debt for a repository as part of the codebase ownership initiative. It is organized around team members regularly reviewing the state of codebases.

Creating a tech day ticket is not required to work on tech debt, but can be a helpful resource to identify areas of focus.

## Recommendations

You will not be able to address the entierety of a codebase tech debt in one go, organization is key to make sure you wrap-up time spent on a codebase with concrete deliverables.

When dedicating a fixed amount of time on a codebase, we recommend the following schedule:
- Begin by reviewing the checklist attached to this ticket, identify and prioritize activities you would be able to complete within the dedicated time.
- Work on said items.
- Wrap up by briefly documenting the changes you did, provide instructions for testing and eventually create issues for activities you'd like to work on next. You can do so by adding a comment to this issue.

## Create tickets for future work
Not all tech debt items can be addressed in one day, one of the goal of the ownership initiative is also to raise awareness about tech debt to be tackled in the future.

When creating such tickets, try to provide details about complexity of such an implementation. These elements play a role in our capacity to prioritize work.

## About priorities

* 🚨 Indicates a required item, to be looked at
* 🔝 Indicates a top priority item
* 🟠 Indicates a medium priority item
* 🙏 Indicates a low priority item

## Checklist

This checklist is there to help you but is not exaustive, if some items are not relevant or should be added, [please request a change](https://github.com/Jahia/.github/blob/main/.github/ISSUE_TEMPLATE/custom_product/tech-day.md).

### General
- [ ] 🚨 I reviewed all OPEN tickets planned for an upcoming release (using codebase-X.Y.Z milestone)
- [ ] 🚨 I reviewed older tech day tickets / ownership activities for that codebase
- [ ] 🔝 I submitted updates to the [tech-day template](https://github.com/Jahia/.github/blob/maintain-list/.github/ISSUE_TEMPLATE/custom_product/tech-day.md) if I noticed incorrect elements
- [ ] 🟠 Standards have been discussed in a tech kumite in the past semester
- [ ] 🙏 Module's license is up-to-date (see https://github.com/Jahia/open-source/blob/main/README.md#licenses)
### Dependency management
- [ ] 🔝 I've identified the process/tools to handle dependency updates (ex: [renovate](https://jahia-confluence.atlassian.net/wiki/spaces/PR/pages/2071358/3rd-party+libraries+-+Ref+ISPOL08.A14024#%5BinlineExtension%5DRenovate))
- [ ] 🟠 Ensure licenses used by the libraries are [Jahia compliant](https://jahia-confluence.atlassian.net/wiki/spaces/PR/pages/2068350/License+check+-+Ref+ISSOP08.A14020)
- [ ] 🙏 Remove unused libraries
### Jahia Modules
- [ ] 🚨 If the codebase is a module shipped with the distribution, the latest version with changes is configured in jahia-pack ([core](https://github.com/Jahia/jahia-pack-private/blob/main/core-modules/pom.xml) or [additional-modules](https://github.com/Jahia/jahia-pack-private/blob/main/additional-modules/pom.xml))
- [ ] 🟠 Make sure dependencies (and appropriate version if needed) are declared in **jahia-depends**
### Static Analysis and code quality
- [ ] 🚨 No Blocker issues on [Sonarqube](https://sonarqube.jahia.com/projects) for the module
- [ ] 🔝 No warnings or errors are present when building the module locally or on GitHub Actions
- [ ] 🔝 The module scores "A" on every [Sonarqube](https://sonarqube.jahia.com/projects) categories for Overall Code
- [ ] 🟠 No Critical/Major issues on [Sonarqube](https://sonarqube.jahia.com/projects) for the module
- [ ] 🟠 I reviewed opportunities to remove dead/unused/unreachable code
- [ ] 🙏 No Minor/Info issues on [Sonarqube](https://sonarqube.jahia.com/projects) for the module
### Javascript
- [ ] 🔝 The module's webpack config is correct ([sample](https://github.com/Jahia/jcontent/blob/main/webpack.config.js))
- [ ] 🔝 The module is using a supported LTS version of ([NodeJS](https://nodejs.org/en/about/previous-releases))
- [ ] 🟠 The module is using React v18+
- [ ] 🟠 The module is using Moonstone v2+
- [ ] 🟠 The module is not using any of the following Jahia's legacy libs:
  * react-material
  * moonstone-alpha
- [ ] 🔝 Dependencies listed in packages.json are still maintained (latest release not older than 6 months)
- [ ] 🟠 Dependencies listed in packages.json are no more than 2 major versions behind their latest release
- [ ] 🟠 Linting is executed properly and show no warnings
- [ ] 🟠 No warning are presents in the browser console when using the app
### Java
- [ ] 🔝 Java dependencies are explicitly declared in the module's pom.xml
- [ ] 🔝 Spring is not used in the module
### Security
- [ ] 🚨 SBOM is generated (configuration available [here](https://jahia-confluence.atlassian.net/wiki/spaces/PR/pages/2071358/3rd-party+libraries+-+Ref+ISPOL08.A14024#%5BinlineExtension%5DSBOM-creation-and-OWASP-Dependency-Track)) and uploaded to [Dependency Track](https://dependency-track-prod.jahia.com/)
- [ ] 🚨 I've checked that inputs validation are applied on both client and server sides
- [ ] 🔝 I've reviewed the security [vulnerabilities](https://sonarqube.jahia.com/issues?resolved=false&types=VULNERABILITY) and [hotspots](https://sonarqube.jahia.com/security_hotspots?id=org.jahia.server%3Ajahia-root) affecting this codebase and discussed it with the Security lead before taking action (Create a [SECURITY](https://support.jahia.com/browse/SECURITY) ticket, Close as false-positive, etc.)
- [ ] 🔝 I've reviewed the [vulnerabilities](https://dependency-track-prod.jahia.com/login?redirect=%2Fprojects) affecting the libraries used by the module (related [documentation](https://jahia-confluence.atlassian.net/wiki/spaces/PR/pages/2079156/Analyzing+vulnerabilities+in+3rd+party+libraries)) and discussed it with the Security lead before taking action (Create a [SECURITY](https://support.jahia.com/browse/SECURITY) ticket, Close as false-positive, etc.)
- [ ] 🔝 A job running Sonar checks (including OWASP Dependency Check) is executed regularly
- [ ] 🔝 I've tested security controls and checked requirements for secure development from [Jahia Application Security Verification Standard](https://jahia-confluence.atlassian.net/wiki/spaces/PR/pages/2074184/Jahia+Application+Security+Verification+Standard)
### QA / Automated Tests
- [ ] 🚨 The codebase is compatible with the latest release of Jahia
- [ ] 🔝 Automated tests are using jahia-cypress for all utils functions
- [ ] 🔝 The test framework is using page-object models published by other modules
- [ ] 🔝 The test framework is publishing its own page-object models for use by others
- [ ] 🟠 A manual-run workflow is available (ex: [manual-run.yml](https://github.com/Jahia/jcontent/blob/main/.github/workflows/manual-run.yml))
- [ ] 🟠 Instructions and [test cases](https://jahia.testrail.net/index.php?/dashboard) are available to document how a release should be tested (how to do the "sanity check" of this module)
- [ ] 🙏 Automated tests are using a recent version of Cypress
- [ ] 🙏 Automated tests are only relying on supported modules
### CI/CD
- [ ] 🔝 The build and the release workflows use the JDK 11 image (only if Jahia Parent is set to 8.2.0.0+) from temurin vendor
- [ ] 🔝 GitHub Actions (nightlys and other workflows) are executed without warnings nor errors (such as depreciations, failed tests, ...)
- [ ] 🙏 The latest version of the actions are used (including jahia-modules-action)
- [ ] 🙏 GitHub Actions [reusable workflows](https://github.com/Jahia/jahia-modules-action/tree/main/.github/workflows) are used
### Documentation
- [ ] 🔝 Readme.md is up-to-date (module purpose, technical details, configuration steps)
- [ ] 🟠 A tech roadmap is available 
- [ ] 🙏 Module's documentation available on the academy is up-to-date
### Issues
- [ ] 🟠 If the repository is public, issues/pull requests from the community have been reviewed and answered, if answer was not possible, a PM/DM was notified.
### GitHub
- [ ] 🚨 [Branch protection](https://jahia-confluence.atlassian.net/wiki/spaces/PR/pages/2067858/GitHub+Product+-+Ref+ISPOL08.A14025#Branch-protection) is enabled for the repository
- [ ] 🟠 The repository uses the organization-level template for the Pull Requests. Make sure the repository does not contain its own template (`.github/PULL_REQUEST_TEMPLATE.md`) that takes precedence over the organization-level one
- [ ] 🙏 **Automatically delete head branches** is selected in **Settings**
- [ ] 🙏 Repository topics match are populated (at a minimum: "product" and "supported")
- [ ] 🙏 Stale branches or branches older than 2 years (non-maintenance branches) have been removed

## Fork checklist

This checklist is focused on our forked repositories

### General
- [ ] 🚨 I checked that we cannot stop using a fork of the library
- [ ] 🚨 I created pull requests to push the fixes done in our fork to the main repository
- [ ] 🚨 I checked that we cannot upgrade to a more recent
- [ ] 🚨 I checked that we've documented why we're still using a fork of this library in [confluence](https://jahia-confluence.atlassian.net/wiki/spaces/PR/pages/2065402/Releasing+our+project+forks)
### Security
- [ ] 🔝 I checked that there are no known security vulnerabilities affecting this codebase
- [ ] 🔝 I've analyzed the [vulnerabilities](https://dependency-track-prod.jahia.com/login?redirect=%2Fprojects) (related [documentation](https://jahia-confluence.atlassian.net/wiki/spaces/PR/pages/2079156/Analyzing+vulnerabilities+in+3rd+party+libraries)) and discussed with the Security lead before taking action (Create a [SECURITY](https://support.jahia.com/browse/SECURITY) ticket, Close as false-positive, etc.)
### CI/CD
- [ ] 🚨 The build and the release/publish workflows are configured (or at least documented in [confluence](https://jahia-confluence.atlassian.net/wiki/spaces/PR/pages/2065402/Releasing+our+project+forks))
### GitHub
- [ ] 🚨 [Branch protection](https://jahia-confluence.atlassian.net/wiki/spaces/PR/pages/2067858/GitHub+Product+-+Ref+ISPOL08.A14025#Branch-protection) is enabled for the repository
- [ ] 🟠 The repository uses the organization-level template for the Pull Requests. Make sure the repository does not contain its own template (`.github/PULL_REQUEST_TEMPLATE.md`) that takes precedence over the organization-level one
- [ ] 🙏 **Automatically delete head branches** is selected in **Settings**
- [ ] 🙏 Repository topics match are populated (at a minimum: "product" and "supported")
