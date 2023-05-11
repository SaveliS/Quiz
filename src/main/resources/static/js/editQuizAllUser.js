const myImage = document.querySelectorAll('.myImage');
const removeImage = document.querySelectorAll('.removeImage');

myImage.forEach(function(img){
    const originalSrc = '/images/edit.png';
    const editSrc = '/images/edit_gif.gif';

    img.addEventListener('mouseover',function(){
        this.src = editSrc;
    });

    img.addEventListener('mouseout', function(){
        this.src = originalSrc;
    });
});

removeImage.forEach(function(img){
    const originalSrc = '/images/remove.png';
    const editSrc = '/images/remove_gif.gif';

    img.addEventListener('mouseover',function(){
        this.src = editSrc;
    });

    img.addEventListener('mouseout',function(){
        this.src = originalSrc;
    });
});

function confirmDelete(quizId, event) {
    event.preventDefault();
    if (confirm("Вы уверены, что хотите удалить эту викторину?")) {
        console.log("Зашел сюда");
        window.location.href = "/quiz/" + quizId +"/remove" +"?confirmed=true";
        console.log("Передал страницу");
    }
}