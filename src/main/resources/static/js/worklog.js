const createButton = document.getElementById("create-btn");

if (createButton) {
    createButton.addEventListener("click", async (e) => {
        const logValue = document.getElementById("log").value;
        const tagValue = document.getElementById("tag").value;
        if (!logValue) {
            alert("로그 내용을 입력하세요.");
            return;
        }
        const tagsArray = Array.from(new Set(tagValue.split(",").map(e => e.trim()).filter(e => e)));


        createButton.disabled = true;
        createButton.textContent = "저장 중...";


        try {
            const response = await fetch("/api/logs", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({log: logValue, tags: tagsArray}),
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || "저장에 실패했습니다.");
            }

            alert("저장 되었습니다.");
            location.replace("/");
        } catch (error) {
            alert("저장에 실패했습니다: " + error.message);
        } finally {
            createButton.disabled = false;
            createButton.textContent = "저장";
        }
    });
}

const modifyButton = document.getElementById("modify-btn");
if (modifyButton) {
    modifyButton.addEventListener("click", (e) => {
        let id = document.getElementById("log-id").value;
        console.log("testid" + id);
        console.log(`/api/logs/${id}`);
        fetch(`/api/logs/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                log: document.getElementById("log").value

            })
        }).then(() => {
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