fetch('api/user/me')
    .then(response =>{
        if (!response.ok){
            location.href = "login.html";
        }
    })