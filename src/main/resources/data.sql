INSERT INTO tb_roles (role_id, name)
VALUES (1, 'ROLE_ADMIN')
    ON CONFLICT (role_id) DO NOTHING;

INSERT INTO tb_roles (role_id, name)
VALUES (2, 'ROLE_MANAGER')
    ON CONFLICT (role_id) DO NOTHING;

INSERT INTO tb_roles (role_id, name)
VALUES (3, 'ROLE_EMPLOYEE')
    ON CONFLICT (role_id) DO NOTHING;

INSERT INTO tb_status (status_id, name)
VALUES (1, 'ACTIVE')
    ON CONFLICT (status_id) DO NOTHING;

INSERT INTO tb_status (status_id, name)
VALUES (2, 'AWAY')
    ON CONFLICT (status_id) DO NOTHING;

INSERT INTO tb_status (status_id, name)
VALUES (3, 'DISMISSED')
    ON CONFLICT (status_id) DO NOTHING;

-- Departamentos
INSERT INTO tb_departments (name)
VALUES ( 'Recursos Humanos')
    ON CONFLICT DO NOTHING;

INSERT INTO tb_departments (name)
VALUES ( 'Tecnologia da Informação')
    ON CONFLICT DO NOTHING;

INSERT INTO tb_departments (name)
VALUES ('Financeiro')
    ON CONFLICT DO NOTHING;

-- Cargos (Positions)
INSERT INTO tb_positions (title, salary_range_min, salary_range_max)
VALUES ('Desenvolvedor Fullstack', 5000.0, 15000.0)
    ON CONFLICT DO NOTHING;

INSERT INTO tb_positions (title, salary_range_min, salary_range_max)
VALUES ('Analista de RH', 4000.0, 10000.0)
    ON CONFLICT DO NOTHING;

INSERT INTO tb_positions (title, salary_range_min, salary_range_max)
VALUES ('Gerente de Projetos', 8000.0, 20000.0)
    ON CONFLICT DO NOTHING;