<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Atualizar Local</title>
</head>
<body>
<div>
    <h1>Formulário de atualização de local</h1>
    <form:form modelAttribute="localUpdateDTO" method="post" action="/local/update">
        <form:hidden path="id"/>
        <h3>Preencher por CEP: </h3>
        <label>
            Cep:
            <form:input path="cep"/>
            <form:errors path="cep"/>
        </label>
        <div>
            <label>Nome:</label>
            <form:input path="name"/>
            <form:errors path="name"/>
        </div>
        <div>
            <label>Código:</label>
            <form:input path="code"/>
            <form:errors path="code"/>
        </div>
        <div>
            <label>Bairro:</label>
            <form:input path="neighborhood"/>
            <form:errors path="neighborhood"/>
        </div>
        <div>
            <label>Cidade:</label>
            <form:input path="city" />
            <form:errors path="city"/>
        </div>
        <form:button>Atualizar</form:button>
    </form:form>
</div>
</body>
<script src="/assets/js/searchCepWithApi.js"></script>
</html>

