truncate table users cascade;
truncate table media cascade;


insert into users(id,email,password,time_created)values
(200,'john@email.com','$2a$10$1ljf1Ki6t9zSwjQtA84bpuMNSAilomTzTApVO6cqcgrTzebao31rW', '2024-06-04T15:03:03.792009700'),
(201, 'jane@email.com','$2a$10$1ljf1Ki6t9zSwjQtA84bpuMNSAilomTzTApVO6cqcgrTzebao31rW', '2024-06-04T15:03:03.792009700'),
(202, 'johnny@email.com','$2a$10$1ljf1Ki6t9zSwjQtA84bpuMNSAilomTzTApVO6cqcgrTzebao31rW', '2024-06-04T15:03:03.792009700'),
(203, 'james@email.com','$2a$10$1ljf1Ki6t9zSwjQtA84bpuMNSAilomTzTApVO6cqcgrTzebao31rW', '2024-06-04T15:03:03.792009700');


insert into media (id, category, description, url, time_created, uploader_id) values
(100, 'ACTION', 'media 1','https://www.cloudinary.com/media1','2024-06-04T15:03:03.792009700', 200),
(102, 'COMEDY', 'media 2','https://www.cloudinary.com/media2','2024-06-04T15:03:03.792009700', 200),
(103, 'STEP_MOM', 'media 3','https://www.cloudinary.com/media3','2024-06-04T15:03:03.792009700', 201),
(104, 'ACTION', 'media 4','https://www.cloudinary.com/media4','2024-06-04T15:03:03.792009700', 200),
(105, 'COMEDY', 'media 5','https://www.cloudinary.com/media5','2024-06-04T15:03:03.792009700', 201);