const createButton = document.getElementById("create-btn");

if (createButton) {
    createButton.addEventListener("click", (e) => {
        fetch("/api/logs", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                log: document.getElementById("log").value
            }),
        }).then(() => {

                alert("저장 되었습니다.");
                location.replace("/");
            }
        )
        ;
    });
}

const modifyButton = document.getElementById("modify-btn");
if (modifyButton) {
    modifyButton.addEventListener("click", (e) => {
        let id = document.getElementById("log-id").value;
        console.log("testid"+id);
        console.log(`/api/logs/${id}`);
        fetch(`/api/logs/${id}`, {
            method: "PUT",
            headers:{
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                log: document.getElementById("log").value

            })
        }).then(()=>{
            alert("수정되었습니다.");
            location.replace("/");
        })
    })
}

const deleteButton = document.getElementsByName("delete-btn");

if (deleteButton) {
    deleteButton.forEach(elememt => elememt.addEventListener(
        'click', (e) => {
            let id = e.target.value;
            console.log(`test : ${id}`);
            console.log(`/api/logs/${id}`);
            fetch(`/api/logs/${id}`, {

                method: "DELETE",

            }).then(
                async (response) => {
                    alert("삭제가 완료되었습니다.");
                    location.replace("/");
                }
            );
        }
    ));

}