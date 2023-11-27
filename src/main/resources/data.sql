INSERT INTO credenciais.tb_usuarios (id, ativa, login, nivel_acesso, senha, bloqueada)
SELECT 1, true, 'adm@gmail.com', 'ROLE_ADM', '$2a$10$vavo6GdtwWFNy6aJQueJM.uXCPjsIwXu4KkwnmzT6Zu8Oa4VClIIm', false
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM credenciais.tb_usuarios
    WHERE id = 1
);
