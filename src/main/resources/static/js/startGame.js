const timeCount = document.querySelector(".timer .timer_sec");
const timeText = document.querySelector(".timer .time_left_txt");
const option_list = document.querySelector(".option_list");
const section_list = document.querySelector("section");

let couter;

function startTimer(time){
    couter = setInterval(timer, 1000);
    function timer(){
        console.log("Checks");
        timeCount.textContent = time;
        time--;
        if(time < 9){
            let addZero = timeCount.textContent;
            timeCount.textContent = "0" + addZero;
        }
        if(time < 0){
            clearInterval(couter);
            timeText.textContent = "Time off";
            const allOptions = option_list.children.length;
            for(let i=0; i < allOptions; i++){
                console.log(option_list.children[i]);
                option_list.children[i].classList.add("disabled"); //once user select an option then disabled all options
                section_list.classList.add("disabled");
                console.log(option_list);
            }
        }
    }
}
startTimer(15);