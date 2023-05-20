const main_form = document.getElementById('main_form');

main_form.addEventListener("submit",function(event){
    if(!isValid()){
        event.preventDefault();
    }
});

function isValid(){
    const email_text = document.getElementById('email_input').value;
    var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if(emailPattern.test(email_text)){
        return true;
    }
    else{
        alert("Пожалуйста, введите корректный email.");
        return false;
    }
}