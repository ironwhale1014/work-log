const createButton = document.getElementById("create-btn");

if (createButton) {
    createButton.addEventListener("click", (ev) => {
        const logValue = document.getElementById("log").value;
        const tagValue = document.getElementById("tag").value;
        const tagsArray = Array.from(new Set(tagValue.split(",").map(e => e.trim()).filter(e => e)));
        let body = JSON.stringify({
            log: logValue, tags: tagsArray
        })
        createButton.disabled = true;
        createButton.textContent = "저장 중...";

        function success() {
            alert("저장 되었습니다.");
            location.replace("/");
        }

        function fail() {
            alert("저장에 실패하였습니다.");
        }

        httpRequest("POST", "/api/logs", body, success, fail)
    });
}

function getCookie(key) {
    let result = null;
    let cookie = document.cookie.split(";");
    cookie.some(function (item) {
        item = item.replace(" ", "");

        let dic = item.split("=");

        if (key === dic[0]) {
            result = dic[1]
            return true
        }
    });

    return result;
}

function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method, headers: {
            Authorization: "Bearer " + localStorage.getItem("access_token"), "Content-Type": "application/json"
        }, body: body
    }).then((response) => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie("refresh_token");
        if (response.status === 401 && refresh_token) {
            fetch("/api/token", {
                method: "POST", headers: {
                    Authorization: "Bearer " + localStorage.getItem("access_token"), "Content-Type": "application/json"
                }, body: JSON.stringify({
                    refreshToken: getCookie("refresh_token"),
                })
            }).then((res) => {
                if (res.ok) {
                    return res.json();
                }
            }).then((result) => {
                localStorage.setItem("access_token", result.accessToken);
                httpRequest(method, url, success, fail);
            }).catch((err) => fail());
        } else {
            return fail();
        }
    })
}


// if (createButton) {
//     createButton.addEventListener("click", async (e) => {
//         const logValue = document.getElementById("log").value;
//         const tagValue = document.getElementById("tag").value;
//         if (!logValue) {
//             alert("로그 내용을 입력하세요.");
//             return;
//         }
//         const tagsArray = Array.from(new Set(tagValue.split(",").map(e => e.trim()).filter(e => e)));
//
//
//         createButton.disabled = true;
//         createButton.textContent = "저장 중...";
//
//
//         try {
//             const response = await fetch("/api/logs", {
//                 method: "POST",
//                 headers: {
//                     "Content-Type": "application/json"
//                 },
//                 body: JSON.stringify({log: logValue, tags: tagsArray}),
//             });
//
//             if (!response.ok) {
//                 const errorData = await response.json();
//                 throw new Error(errorData.message || "저장에 실패했습니다.");
//             }
//
//             alert("저장 되었습니다.");
//             location.replace("/");
//         } catch (error) {
//             alert("저장에 실패했습니다: " + error.message);
//         } finally {
//             createButton.disabled = false;
//             createButton.textContent = "저장";
//         }
//     });
// }

const modifyButton = document.getElementById("modify-btn");
if (modifyButton) {
    modifyButton.addEventListener("click", (e) => {
        let id = document.getElementById("log-id").value;
        let tagValue = document.getElementById("tag").value;

        const tagsArray = Array.from(new Set(tagValue.split(",").map(e => e.trim()).filter(e => e)));

        body = JSON.stringify({
            log: document.getElementById("log").value, tags: tagsArray
        });

        function success() {
            alert("수정이 완료되었습니다.");
            location.replace("/");
        }

        function fail() {
            alert("수정이 실패했습니다.");
            location.replace("/");
        }

        httpRequest("PUT", `/api/logs/${id}`, body, success, fail);

    });
}

const deleteButton = document.getElementsByName("delete-btn");

if (deleteButton) {
    deleteButton.forEach((ev) => ev.addEventListener('click', (e) => {
        let id = e.target.value;
        console.log(`test : ${id}`);
        console.log(`/api/logs/${id}`);

        function success() {
            alert("삭제가 완료되었습니다.");
            location.replace("/");
        }

        function fail() {
            alert("삭제가 실패했습니다.");
            location.replace("/")
        }

        httpRequest("DELETE", `/api/logs/${id}`, null, success, fail);

    }));

}