$(function () {
    Generator.initialize();
});

var Generator = {
    initialize: function() {
        Generator.setupRemoveModelField();
        Generator.setupExtensionClassRadios();
    },
    setupRemoveModelField: function(){
        $('button.removeModelField')
            .off('click.removeModelField')
            .on('click.removeModelField', function(){
                var $button = $(this);

                // Get the index of this remove button, same index of the field to remove
                var removeIndex = $('button.removeModelField').index($button);

                $('#modelFieldToRemove').val(removeIndex);
            })
    },
    setupExtensionClassRadios: function(){
        $('.ui.radio.checkbox').checkbox();
    }
};