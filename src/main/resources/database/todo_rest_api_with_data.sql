--
-- База данных: `todo_rest_api`
--

-- --------------------------------------------------------

--
-- Структура таблицы `roles`
--

CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

--
-- Дамп данных таблицы `roles`
--

INSERT INTO roles (id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE users
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    email     VARCHAR(255) NOT NULL,
    firstname VARCHAR(128) NOT NULL,
    lastname  VARCHAR(128) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    aboutMe   VARCHAR(2000),
    avatar    VARCHAR(255),
    is_active BOOLEAN      NOT NULL DEFAULT TRUE,
    created   DATE                  DEFAULT CURRENT_DATE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

--
-- Дамп данных таблицы `users`
--

INSERT INTO users (id, email, firstname, lastname, password,
                   aboutMe, avatar, is_active, created)
VALUES (1,
        'tanya@gmail.com',
        'Tatyana',
        'Emeyanovich',
        '$2a$10$cqx7JMC9YyJ3nXON/2sv0.qUpIq68Syd7fFW3MTS2OZEt59uFEl4e',
        'vsu.by',
        'https://mydomain.com/images/user_1.png',
        true,
        '2025-02-04'),
       (2,
        'klimko@gmail.com',
        'Yuriy',
        'Klimko',
        '$2a$10$iNtgOvYXgkWUk1tKyTPS/OG6x3k0Vx3VCLG7lXKnQsmzjQvoBXKqe',
        'vsu.by, course 1',
        'https://mydomain.com/images/user_2.png',
        true,
        '2025-03-04');

-- --------------------------------------------------------

--
-- Структура таблицы `tasks`
--

CREATE TABLE tasks
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(2000),
    user_id     BIGINT       NOT NULL,
    startDate   DATE,
    endDate     DATE,
    created     DATE DEFAULT CURRENT_DATE,
    updated     DATE,
    status      VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

--
-- Дамп данных таблицы `tasks`
--

INSERT INTO tasks (title, description, user_id,
                   startDate, endDate, created, updated, status)
VALUES ('Learn CSS', 'Learn CSS by Book Devid McFarland', 1, '2025-02-06', '2025-02-26', '2025-02-06', '2025-02-06', 'ACTIVE'),
       ('Learn HTML', 'Learn HTML by course on service \'htmlacademy.ru\'', 1, '2025-02-10', '2025-02-20', '2025-02-10', '2025-02-10', 'COMPLETED'),
       ('Learn JS', 'Learn JS by course on website \'learnjs.ru\'', 1, '2025-03-02', '2025-03-20', '2025-03-02', '2025-05-08', 'ACTIVE'),
       ('Learn JS', 'learn JS', 2, '2025-03-06', '2025-03-10', '2025-03-06', '2025-03-08', 'ACTIVE'),
       ('Learn ReactJS', 'Learn ReactJS by course "IT-Camasutra"', 2, '2025-03-09', '2025-03-26', '2025-03-09', '2025-03-12', 'ACTIVE'),
       ('Complete coursework for the session', 'coursework by ОАиП', 2, '2025-03-20', '2025-03-25', '2025-03-20', '2025-03-21', 'COMPLETED');

-- --------------------------------------------------------

--
-- Структура таблицы `user_roles`
--

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

--
-- Дамп данных таблицы `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`)
VALUES (1, 1),
       (1, 2),
       (2, 1);
