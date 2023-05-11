const editForm = document.getElementById("edit_form");

editForm.addEventListener("submit",function (event){
    if(!isValid()){
        event.preventDefault();
    }
});

function isValid(){
    var inputs = document.querySelectorAll(".new_answer");
    var input = document.querySelectorAll(".new_question");
    var isValid = true;

    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].value === "") {
            isValid = false;
            break;
        }
    }
    for (var i = 0; i < input.length; i++){
        if (input[i].value === "") {
            isValid = false;
            break;
        }
    }
    if (!isValid) {
        alert("Заполните все поля формы!");
    }
    return isValid;
};