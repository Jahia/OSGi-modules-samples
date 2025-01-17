var serverContext = (typeof contextJsParameters != 'undefined') ? contextJsParameters.contextPath : '';

/** Since this is a fragment, resources are added to fragment-host, so we use path of host module (/modules/ckeditor) here */
CKEDITOR.plugins.addExternal('texttransform', serverContext + '/modules/ckeditor/javascript/ckeditor/plugins/texttransform/plugin.js');

CKEDITOR.editorConfig = function(config) {
    console.log('Initializing custom ckeditor config from fragment...');

    // mathjax already included in ckeditor module
    config.extraPlugins='mathjax,texttransform';
    config.toolbar_Full = [
        {name: 'document', items: ['Source', '-', 'NewPage', 'Templates', '-', 'Undo', 'Redo', 'Mathjax']},
        {name: 'texttransform', items: ['TransformTextToUppercase', 'TransformTextToLowercase']}, // custom plugins
    ];
}
