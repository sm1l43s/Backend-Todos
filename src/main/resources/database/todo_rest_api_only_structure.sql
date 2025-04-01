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

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE users
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    email     VARCHAR(255) NOT NULL UNIQUE,
    firstname VARCHAR(128) NOT NULL,
    lastname  VARCHAR(128) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    about_me   VARCHAR(2000),
    avatar    BLOB,
    is_active BOOLEAN      NOT NULL DEFAULT TRUE,
    created   DATE                  DEFAULT (CURRENT_DATE)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

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
    start_date   DATE,
    end_date     DATE,
    created     DATE DEFAULT (CURRENT_DATE),
    updated     DATE,
    status      VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;