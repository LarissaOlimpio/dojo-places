document.addEventListener("DOMContentLoaded", function() {
    const cepInput = document.getElementById("cep");
    cepInput.addEventListener("input", (e) => {
        e.preventDefault();
        const cep = cepInput.value;
        if (cep.trim().length == 8) {
            fetch('https://viacep.com.br/ws/'+ cep +'/json/')
                .then(response => response.json())
                .then(data => {
                    document.getElementById('city').value = data.localidade;
                    document.getElementById('neighborhood').value = data.logradouro;
                }).catch(err => console.log(err));
        }
    });
});
