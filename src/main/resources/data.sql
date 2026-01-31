-- Roles
INSERT INTO tb_roles (role_id, name) VALUES (1, 'ROLE_ADMIN') ON CONFLICT (role_id) DO NOTHING;
INSERT INTO tb_roles (role_id, name) VALUES (2, 'ROLE_MANAGER') ON CONFLICT (role_id) DO NOTHING;
INSERT INTO tb_roles (role_id, name) VALUES (3, 'ROLE_EMPLOYEE') ON CONFLICT (role_id) DO NOTHING;

-- Status
INSERT INTO tb_status (status_id, name) VALUES (1, 'ACTIVE') ON CONFLICT (status_id) DO NOTHING;
INSERT INTO tb_status (status_id, name) VALUES (2, 'AWAY') ON CONFLICT (status_id) DO NOTHING;
INSERT INTO tb_status (status_id, name) VALUES (3, 'DISMISSED') ON CONFLICT (status_id) DO NOTHING;
