const removeImage = document.querySelectorAll('.remove_img');

removeImage.forEach(function(img){
    const originalSrc = '/images/remove.png';
    const editSrc = '/images/remove_gif.gif';

    img.addEventListener('mouseover',function(){
        this.src = editSrc;
    });

    img.addEventListener('mouseout', function(){
        this.src = originalSrc;
    });
});