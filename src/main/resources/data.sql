INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO categories (name)
VALUES ('Beauty'),
       ('House renovation'),
       ('Upholstery'),
       ('Car Restoration'),
       ('Cleaning');


INSERT INTO users (name, email, password, city_name, avatar, about_me, status, is_approved,
                   approved_by_user_id,
                   approved_date, contact_id)
VALUES ('Patrycja', 'beforeandafter757@gmail.com', 'password123', 'CityA',
        NULL, '        ''Nasza firma, Dom-Odnova, to esencja rodzinnej pasji do renowacji i odrodzenia piękna starych domów, nurt, który płynie w naszych żyłach od pokoleń. Historia naszego przedsiębiorstwa sięga początków dwudziestego wieku, kiedy to pradziadek Antoni, z zawodu cieśla i mistrz stolarski, rozpoczął przygodę z odbudową zniszczonych dworków. Jego praca, pełna zaangażowania i miłości do detalu, szybko zdobyła uznanie wśród lokalnej społeczności.

        Po latach pałeczka przekazywana była z ojca na syna, aż dotarła do mnie – prawnuka Antoniego. Współcześnie, Dom-Odnova łączy tradycyjne metody rzemieślnicze z nowoczesnymi technologiami, by przywracać dawny blask budynkom z duszą. Każdy projekt traktujemy jak dzieło sztuki, dokładając wszelkich starań, aby odzwierciedlić w nim zarówno historię murów, jak i osobowość ich mieszkańców.

        Nasza praca to nie tylko renowacja – to dialog z przeszłością i pieczołowite tworzenie przestrzeni, która będzie służyć kolejnym pokoleniom. Dumni z naszego dziedzictwa, z zapałem wyruszamy naprzeciw nowym wyzwaniom, pamiętając o rzemiośle naszych przodków i przekazując tę pasję dalej. Dom-Odnova to więcej niż firma – to opowieść o domach, które dzięki naszej pracy mogą opowiadać własne historie.'',', 'TO_REVIEW', TRUE,
        NULL, NULL, (SELECT id FROM contact_details WHERE user_id = 1)),

       ('Magda', 'magda@example.com', 'password123', 'CityB',
        NULL, 'About Magda',
        'TO_REVIEW', TRUE, NULL, NULL,(SELECT id FROM contact_details WHERE user_id = 2)),

       ('Marcin', 'marcin@example.com', 'password123', 'CityC',
        NULL, 'About Marcin',
        'USER_REVISION', FALSE, NULL, NULL,(SELECT id FROM contact_details WHERE user_id = 3)),

       ('User1', 'user1@example.com', 'password456', 'CityD',
        NULL, 'About User1',
        'TO_REVIEW', TRUE, NULL, NULL,    (SELECT id FROM contact_details WHERE user_id = 4)),

       ('User2', 'user2@example.com', 'password456', 'CityE',
        NULL, 'About User2', 'TO_REVIEW', TRUE,
        NULL, NULL,     (SELECT id FROM contact_details WHERE user_id = 5)),

       ('User3', 'user3@example.com', 'password456', 'CityF',
        NULL,
        'Nasza firma, Dom-Odnova, to esencja rodzinnej pasji do renowacji i odrodzenia piękna starych domów, nurt, który płynie w naszych żyłach od pokoleń. Historia naszego przedsiębiorstwa sięga początków dwudziestego wieku, kiedy to pradziadek Antoni, z zawodu cieśla i mistrz stolarski, rozpoczął przygodę z odbudową zniszczonych dworków. Jego praca, pełna zaangażowania i miłości do detalu, szybko zdobyła uznanie wśród lokalnej społeczności.

        Po latach pałeczka przekazywana była z ojca na syna, aż dotarła do mnie – prawnuka Antoniego. Współcześnie, Dom-Odnova łączy tradycyjne metody rzemieślnicze z nowoczesnymi technologiami, by przywracać dawny blask budynkom z duszą. Każdy projekt traktujemy jak dzieło sztuki, dokładając wszelkich starań, aby odzwierciedlić w nim zarówno historię murów, jak i osobowość ich mieszkańców.

        Nasza praca to nie tylko renowacja – to dialog z przeszłością i pieczołowite tworzenie przestrzeni, która będzie służyć kolejnym pokoleniom. Dumni z naszego dziedzictwa, z zapałem wyruszamy naprzeciw nowym wyzwaniom, pamiętając o rzemiośle naszych przodków i przekazując tę pasję dalej. Dom-Odnova to więcej niż firma – to opowieść o domach, które dzięki naszej pracy mogą opowiadać własne historie.',
        'USER_REVISION', FALSE, NULL, NULL,    (SELECT id FROM contact_details WHERE user_id = 6)),

       ('User4', 'user4@example.com', 'password456', 'CityG',
        NULL,
        'About User4', 'USER_REVISION', FALSE, NULL, NULL, (SELECT id FROM contact_details WHERE user_id = 7));

INSERT INTO users_roles (id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'beforeandafter757@gmail.com'), (SELECT role_id FROM roles WHERE name = 'ROLE_ADMIN')),
       ((SELECT id FROM users WHERE email = 'magda@example.com'), (SELECT role_id FROM roles WHERE name = 'ROLE_ADMIN')),
       ((SELECT id FROM users WHERE email = 'marcin@example.com'), (SELECT role_id FROM roles WHERE name = 'ROLE_ADMIN')),
       ((SELECT id FROM users WHERE email = 'user1@example.com'), (SELECT role_id FROM roles WHERE name = 'ROLE_USER')),
       ((SELECT id FROM users WHERE email = 'user2@example.com'), (SELECT role_id FROM roles WHERE name = 'ROLE_USER')),
       ((SELECT id FROM users WHERE email = 'user3@example.com'), (SELECT role_id FROM roles WHERE name = 'ROLE_USER')),
       ((SELECT id FROM users WHERE email = 'user4@example.com'), (SELECT role_id FROM roles WHERE name = 'ROLE_USER'));

INSERT INTO contact_details (user_id, street_name, street_number, apart_number, post_code, city_name, phone_number, email, webpage)
VALUES
    (1, 'Bukowa', '1', '2A', '10001', 'Warsaw', '666111333', 'beforeandafter757@gmail.com', 'www.example1.com'),
    (2, 'Akacjowa', '2', '5B', '10002', 'Cracow', '999555666', 'user2@example.com', 'www.example2.com'),
    (3, 'Świerkowa', '3', '12', '10003', 'Poznan', '777111222', 'user3@example.com', 'www.example3.com'),
    (4, 'Kaktusowa', '4', '4D', '10004', 'Warsaw', '890980976', 'user4@example.com', 'www.example4.com'),
    (5, 'Pistacjowa', '5', '9C', '10005', 'Gdansk', '515112098', 'user5@example.com', 'www.example5.com'),
    (6, 'Migdałowa', '6', '10E', '10006', 'Warsaw', '3505202', 'user6@example.com', 'www.example6.com'),
    (7, 'Fistaszkowa', '7', '7F', '10007', 'Cracow', '44555666', 'user7@example.com', 'www.example7.com');


-- INSERT INTO images
-- (file, category_id, description, city_name, user_id, is_approved, approved_by_user_id, approved_date, status)
-- VALUES ('https://i.insider.com/5f15db39f34d0566ad407306?width=700', 1, 'Description1', 'Warsaw', 1, TRUE, 2,
--         '2023-10-29', 'ACCEPTED'),
--        ('https://thinkwood-wordpress.s3.amazonaws.com/wp-content/uploads/2021/09/22160454/ThinkWood_BeforeAfter-LivingRoom-01.png',
--         2, 'Description2', 'Cracow', 2, TRUE, 1, '2023-10-28', 'ACCEPTED'),
--        ('https://www.younghouselove.com/wp-content/uploads/2018/09/Beach-House-Before-After-Doorway-Side-By-Side-650x457.jpg',
--         1, 'Description3', 'Cracow', 3, FALSE, NULL, NULL, 'TO_REVIEW'),
--        ('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRu5DIHmy951O9FLSLPNdix22i7SfDeijyeNw&usqp=CAU', 2,
--         'Description4', 'Warsaw', 4, TRUE, 3, '2023-10-27', 'ACCEPTED'),
--        ('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgn90OZSJ7hUaVV67326jeg_f0cYwrS2FFVw&usqp=CAU', 1,
--         'Description5', 'Cracow', 5, FALSE, NULL, NULL, 'TO_REVIEW'),
--        ('https://i.pinimg.com/originals/7c/64/6c/7c646cc9678ad5788f275dfa5f53084d.jpg', 2, 'Description6', 'Cracow', 6,
--         TRUE, 1, '2023-10-26', 'ACCEPTED'),
--        ('https://i0.wp.com/detourdetroiter.com/wpcom-142100448/wp-content/uploads/2020/09/north-end-reno-1-1.jpg?fit=1265%2C838&ssl=1',
--         1, 'Description7', 'Sopot', 7, FALSE, NULL, NULL, 'TO_REVIEW');



-- UPDATE images
-- SET description = 'opis usługi'
-- WHERE id = 6
--   AND user_id = 6;
