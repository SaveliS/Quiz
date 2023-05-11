const fileInput = document.getElementById('file_upload');
const fileName = document.getElementById('file_name');

fileInput.addEventListener('change', function() {
    const file = fileInput.files[0];

    if (file.type.startsWith('image/')) {
        updateInputFileText(this);
    } else {
        // файл не является изображением, нужно вывести сообщение об ошибке или предупреждение
        alert('Выбранный файл не является изображением!');
        // очистить выбранный файл
        fileInput.value = '';
    }
});

function updateInputFileText(inputFile) {
    let file = inputFile.files[0];
    $(inputFile).closest('.input-file').find('.input-file-text').html(file.name);
}