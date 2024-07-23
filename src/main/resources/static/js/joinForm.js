const idCheck = () => {
    const username = document.getElementById("username").value;
    const pattern = /^[A-Za-z0-9][A-Za-z0-9]*$/;
    const checkResult = document.getElementById("id-result");
    const checkResult2 = document.getElementById("id-nonDuplication");

	if(username.length<4 || username.length>10 || !pattern.test(username)){
	    checkResult.style.color = "black";
		checkResult.innerHTML = "영문,숫자로 4~10자 여야합니다.";
		checkResult2.checked = false;
	}else{
        $.ajax({
            type: "post",
            url: "/user/username-check",
            data: {
                "username": username
            },
            success: function(res) {
                console.log("요청성공", res);
                if (res == "ok") {
                    checkResult.style.color = "#03A3F1";
                    checkResult.innerHTML = "사용가능한 아이디입니다.";
                    checkResult2.checked = true;
                } else {
                    checkResult.style.color = "red";
                    checkResult.innerHTML = "이미 사용중인 아이디입니다.";
                    checkResult2.checked = false;
                }
            },
            error: function(err) {
                console.log("에러발생", err);
            }
        });
	}
}

const pwCheck = () => {
    const password = document.getElementById("password").value;
    const checkResult = document.getElementById("pw-result");
    const checkResult2 = document.getElementById("pw-nonDuplication");

	if(password.length<5){
	    checkResult.style.color = "black";
		checkResult.innerHTML = "5자 이상이어야합니다.";
		checkResult2.checked = false;
	}else{
        checkResult.style.color = "#03A3F1";
        checkResult.innerHTML = "사용가능한 비밀번호입니다.";
        checkResult2.checked = true;
	}
}

const emailCheck = () => {
    const email = document.getElementById("email").value;
    email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
    const checkResult = document.getElementById("email-result");
    const checkResult2 = document.getElementById("email-nonDuplication");

	if(!email_regex.test(email)){
	    checkResult.style.color = "black";
		checkResult.innerHTML = "이메일형식에 맞게 입력해주세요.";
		checkResult2.checked = false;
	}else{
        $.ajax({
            type: "post",
            url: "/user/email-check",
            data: {
                "email": email
            },
            success: function(res) {
                console.log("요청성공", res);
                if (res == "ok") {
                    checkResult.style.color = "#03A3F1";
                    checkResult.innerHTML = "사용가능한 이메일입니다.";
                    checkResult2.checked = true;
                } else {
                    checkResult.style.color = "red";
                    checkResult.innerHTML = "이미 사용중인 이메일입니다.";
                    checkResult2.checked = false;
                }
            },
            error: function(err) {
                console.log("에러발생", err);
            }
        });
	}
}

const nicknameCheck = () => {
    const nickname = document.getElementById("nickname").value;
    const checkResult = document.getElementById("nickname-result");
    const checkResult2 = document.getElementById("nickname-nonDuplication");

	if(nickname.length<3 || nickname.length>8){
	    checkResult.style.color = "black";
		checkResult.innerHTML = "3~8글자여야합니다.";
		checkResult2.checked = false;
	}else{
        $.ajax({
            type: "post",
            url: "/user/nickname-check",
            data: {
                "nickname": nickname
            },
            success: function(res) {
                console.log("요청성공", res);
                if (res == "ok") {
                    checkResult.style.color = "#03A3F1";
                    checkResult.innerHTML = "사용가능한 닉네임입니다.";
                    checkResult2.checked = true;
                } else {
                    checkResult.style.color = "red";
                    checkResult.innerHTML = "이미 사용중인 닉네임입니다.";
                    checkResult2.checked = false;
                }
            },
            error: function(err) {
                console.log("에러발생", err);
            }
        });
	}
}

const validateForm = () => {
    var checkbox = document.getElementById('id-nonDuplication');
    if (!checkbox.checked) {
        alert('사용중인 아이디입니다.');
        return false;
    }

    checkbox = document.getElementById('pw-nonDuplication');
    if (!checkbox.checked) {
        alert('비밀번호는 5자 이상이어야합니다.');
        return false;
    }

    checkbox = document.getElementById('email-nonDuplication');
    if (!checkbox.checked) {
        alert('사용중인 이메일입니다.');
        return false;
    }

    checkbox = document.getElementById('nickname-nonDuplication');
    if (!checkbox.checked) {
        alert('사용중인 닉네임입니다.');
        return false;
    }


    return true;
}



