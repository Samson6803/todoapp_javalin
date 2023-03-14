fetch('api/user/me')
    .then(response =>{
        if (response.ok){
            location.href = "notes.html";
        }
    })