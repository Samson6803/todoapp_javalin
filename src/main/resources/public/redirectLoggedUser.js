fetch('api/user/me', {
    async: false
})
    .then(response =>{
        if (response.ok){
            location.href = "notes.html";
        }
    })