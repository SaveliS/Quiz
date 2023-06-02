document.getElementById('quizForm').addEventListener('submit', function(event) {
    if (!validateForm()) {
        event.preventDefault(); // Отменяем отправку формы
    }
});

function validateForm() {
    // Получаем все поля формы
    var formFields = document.getElementById('quizForm').querySelectorAll('input[type="text"]');

    // Перебираем поля и проверяем, что они не пустые
    for (var i = 0; i < formFields.length; i++) {
        var field = formFields[i];

        // Проверяем, что поле пустое
        if (field.value.trim() === '') {
            alert('Пожалуйста, заполните все поля.');
            return false;
        }
    }
    if(!validateFormCheckBox()){
        return false;
    }
    // Все поля заполнены, отправляем форму
    return true;
}

function validateFormCheckBox() {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    var checkedCount = 0;

    // Перебираем все флажки и считаем количество отмеченных флажков
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            checkedCount++;
        }
    }

    // Проверяем, что хотя бы один флажок отмечен
    if (checkedCount === 0) {
        alert('Пожалуйста, выберите хотя бы один вариант.');
        return false;
    }

    if(checkedCount === checkboxes.length){
        alert('Пожалуйста, хотя бы один вариант ответа должен быть неверным.');
        return false;
    }

    // Все проверки прошли успешно, отправляем форму
    return true;
}