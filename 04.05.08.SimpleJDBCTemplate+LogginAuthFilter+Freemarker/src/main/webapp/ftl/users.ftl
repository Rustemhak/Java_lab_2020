<html>
<head>
    <title>Title</title>
</head>
<body>

<table>
    <th>ID</th>
    <th>FIRST NAME</th>
    <th>LAST NAME</th>
    <th>AGE</th>

    <#list usersForJsp as user>
        <tr>
            <td>
                ${user.id}
            </td>
            <td>
                ${user.firstName}
            </td>
            <td>
                ${user.lastName}
            </td>
            <td>
                ${user.age}
            </td>
        </tr>
    </#list>

</table>

</body>
</html>
