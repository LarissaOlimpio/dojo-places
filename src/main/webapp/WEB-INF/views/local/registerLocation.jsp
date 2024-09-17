<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Cadastrar Local</title>
    <link rel="stylesheet" type="text/css" href="/assets/css/form/form.css">
</head>
<body>
<div class="form-container">
    <h1>Cadastro de local</h1>
    <form:form modelAttribute="localCreateDTO" method="post" action="/local/submit">
        <p>Preencher por CEP: </p>
        <label>
            Cep:
            <form:input path="cep"/>
            <form:errors path="cep" cssClass="error"/>
        </label>
        <div>
            <label>Nome:</label>
            <form:input path="name"/>
            <form:errors path="name" cssClass="error"/>
        </div>
        <div>
            <label>Código:</label>
            <form:input path="code"/>
            <form:errors path="code" cssClass="error"/>
        </div>
        <div>
            <label>Bairro:</label>
            <form:input path="neighborhood"/>
            <form:errors path="neighborhood" cssClass="error"/>
        </div>
        <div>
            <label>Cidade:</label>
            <form:input path="city"/>
            <form:errors path="city" cssClass="error"/>
        </div>
        <div class="button">
            <form:button>Cadastrar</form:button>
        </div>
    </form:form>
</div>
<script src="/assets/js/searchCepWithApi.js"></script>
</body>
</html>
