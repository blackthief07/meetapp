INSERT INTO user (id, name, password) VALUES (1, 'user1', '$2a$04$GJ85Ihcglhbqac2zc3z3A.C3v55FMmN8.qGQ8FlNBCuyLtQ5/TyMO');
INSERT INTO user (id, name, password) VALUES (2, 'user2', '$2a$04$XWpgKkCQaVRuXjB5f1hzt.pDa2NAzntroH3bELICZy8R8Q0L0SShO');
INSERT INTO user (id, name, password) VALUES (3, 'user3', '$2a$04$bmmWXec6YkFIesIxv2gx7.NbxRcMJ4UoRKT9Qoqk09pKegfcr3Zla');

INSERT INTO role (id, description, name) VALUES (1, 'Admin role', 'ADMIN');
INSERT INTO role (id, description, name) VALUES (2, 'User role', 'USER');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);

INSERT INTO meet_up (id, description, date, max_attendees) VALUES (1, 'user1 birthday', {ts '2020-03-20'}, 10);

INSERT INTO check_in (meetup_id, user_id, checked_in) VALUES (1, 2, false);
INSERT INTO check_in (meetup_id, user_id, checked_in) VALUES (1, 3, false);