INSERT INTO `forms` (`id`, `created_at`, `description`, `order_display`, `status`, `title`, `updated_at`) VALUES
(1, '2026-05-03 07:20:48.134436', 'Employee can register OT',                    1, 'ACTIVE', 'Resgister OT',                              NULL),
(2, '2026-05-03 07:35:36.054029', 'Employee can register training session',       5, 'ACTIVE', 'Resgister Training Session for DevOps',     '2026-05-03 07:52:14.251246'),
(3, '2026-05-03 07:52:04.295733', 'Employee can request for coming late',         4, 'ACTIVE', 'Request for coming late 04/05/2026',        NULL);


INSERT INTO `fields` (`id`, `created_at`, `label`, `options`, `order_display`, `required`, `type`, `updated_at`, `form_id`) VALUES

(4,  '2026-05-03 07:34:32.531066', 'name',          '',                                                          2, b'1', 'TEXT',   NULL,                        1),
(5,  '2026-05-03 07:34:32.562976', 'Employee ID',   '',                                                          1, b'1', 'TEXT',   NULL,                        1),
(6,  '2026-05-03 07:34:32.564920', 'Working Time',  '8h30AM - 9h30AM Sat; 9h30AM - 11h30AM Sat',                3, b'1', 'SELECT', NULL,                        1),

(7,  '2026-05-03 07:37:48.716371', 'Employee Name', '',                                                          2, b'1', 'TEXT',   '2026-05-03 15:31:42.787864', 2),
(9,  '2026-05-03 07:37:48.720944', 'Class',         'A: 03/05/2026-03/06/2026; B: 10/05/2026-10/06/2026',       3, b'1', 'SELECT', NULL,                        2),

(11, '2026-05-03 15:36:37.197064', 'Employee ID',   '',                                                          1, b'1', 'TEXT',   NULL,                        3),
(12, '2026-05-03 15:36:37.200065', 'Reason',        '',                                                          3, b'1', 'TEXT',   NULL,                        3),
(13, '2026-05-03 15:47:09.513864', 'Name',          '',                                                          2, b'1', 'TEXT',   NULL,                        3);


INSERT INTO `submissions` (`id`, `submit_at`, `form_id`) VALUES
(1, '2026-05-03 07:44:20.135040', 1);



INSERT INTO `submission_value` (`id`, `value`, `field_id`, `submission_id`) VALUES
(1, 'vinh',          4, 1),   -- name
(2, '220192138',     5, 1),   -- Employee ID
(3, '8h30AM - 9h30AM Sat', 6, 1); -- Working Time