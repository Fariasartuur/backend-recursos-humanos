-- Roles
INSERT INTO tb_roles (role_id, name) VALUES (1, 'ROLE_ADMIN') ON CONFLICT (role_id) DO NOTHING;
INSERT INTO tb_roles (role_id, name) VALUES (2, 'ROLE_MANAGER') ON CONFLICT (role_id) DO NOTHING;
INSERT INTO tb_roles (role_id, name) VALUES (3, 'ROLE_EMPLOYEE') ON CONFLICT (role_id) DO NOTHING;

-- Status
INSERT INTO tb_status (status_id, name) VALUES (1, 'ACTIVE') ON CONFLICT (status_id) DO NOTHING;
INSERT INTO tb_status (status_id, name) VALUES (2, 'AWAY') ON CONFLICT (status_id) DO NOTHING;
INSERT INTO tb_status (status_id, name) VALUES (3, 'DISMISSED') ON CONFLICT (status_id) DO NOTHING;

-- Departamentos
INSERT INTO tb_departments (department_id, name) VALUES (1, 'Recursos Humanos') ON CONFLICT (department_id) DO NOTHING;
INSERT INTO tb_departments (department_id, name) VALUES (2, 'Tecnologia da Informação') ON CONFLICT (department_id) DO NOTHING;
INSERT INTO tb_departments (department_id, name) VALUES (3, 'Financeiro') ON CONFLICT (department_id) DO NOTHING;

-- Cargos (Positions)
INSERT INTO tb_positions (position_id, title, salary_range_min, salary_range_max)
VALUES (1, 'Desenvolvedor Fullstack', 5000.0, 15000.0) ON CONFLICT (position_id) DO NOTHING;

INSERT INTO tb_positions (position_id, title, salary_range_min, salary_range_max)
VALUES (2, 'Analista de RH', 4000.0, 10000.0) ON CONFLICT (position_id) DO NOTHING;

INSERT INTO tb_positions (position_id, title, salary_range_min, salary_range_max)
VALUES (3, 'Gerente de Projetos', 8000.0, 20000.0) ON CONFLICT (position_id) DO NOTHING;

-- 5. Escalas
INSERT INTO tb_scales (scale_id, name, description)
VALUES (1, 'Administrativo 5x2', 'Segunda a Sexta, 08h às 17h') ON CONFLICT (scale_id) DO NOTHING;

-- 6. Configuração dos Dias da Escala (Exemplo Segunda a Sexta)
INSERT INTO tb_scale_day_configs (scale_id, day_order, is_work_day, planned_entry, planned_exit) VALUES
                                                                                                     (1, 1, true, '08:00:00', '17:00:00'),
                                                                                                     (1, 2, true, '08:00:00', '17:00:00'),
                                                                                                     (1, 3, true, '08:00:00', '17:00:00'),
                                                                                                     (1, 4, true, '08:00:00', '17:00:00'),
                                                                                                     (1, 5, true, '08:00:00', '17:00:00'),
                                                                                                     (1, 6, false, null, null),
                                                                                                     (1, 7, false, null, null)
    ON CONFLICT DO NOTHING;

-- 7. Funcionário de Teste (Vinculado aos IDs acima)
INSERT INTO tb_employees (employee_id, nome, cpf, base_salary, health_plan, admission_date, department_id, position_id, status_id, scale_id, scale_start_date)
VALUES (
           '550e8400-e29b-41d4-a716-446655440000',
           'Artur Silva',
           '123.456.789-00',
           7500.00,
           350.00,
           '2026-01-01',
           2, -- Tecnologia da Informação
           1, -- Desenvolvedor Fullstack
           1, -- Active
           1, -- Administrativo 5x2
           '2026-01-05' -- Data de início (segunda-feira)
       ) ON CONFLICT (employee_id) DO NOTHING;