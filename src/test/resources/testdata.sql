INSERT INTO genre (id, name)
VALUES ('ee1fd24c-786b-59c4-a712-4428ee65b61d', 'Детектив'),
			 ('52b18bcc-3a12-518b-8ee2-22132652a82c', 'Фантастика'),
			 ('8bb754ba-fb77-5a41-acc0-56fd6de7303c', 'Триллер')
ON CONFLICT
	DO NOTHING;


INSERT INTO author (id, name, birth)
VALUES ('2dabc86c-fc3a-58d3-b199-7386415ae67d', 'Михаил Афанасьевич Булгаков', '1891-05-15'::DATE),
			 ('0b2041fc-002f-5734-aa7b-4d1eb8661c59', 'Оскар Уайльд', '1854-10-16'::DATE),
			 ('6e5cfc16-54ed-56ba-9a59-4fe5761f6c2c', 'Лев Николаевич Толстой', '1910-11-20'::DATE)
ON CONFLICT
	DO NOTHING;

COMMIT;

INSERT INTO book (id, name, year, page_count, isbn)
VALUES ('802ae788-4c31-4d92-9055-bfcc6e9cfc5c', 'book1', 2007, 121, 'ISBN 5-17-008508-7'),
			 ('d8e483b0-eb6e-4c77-a100-d84e49bb35fc', 'book2', 2009, 341, 'ISBN 966-596-437-2'),
			 ('adbed02c-78ff-4de9-aab2-c086f9a3ec39', 'book3', 2008, 131, 'ISBN 978-94-007-5948-0'),
			 ('5518b41d-9e04-4cbb-90a7-9b8334771db9', 'book4', 2010, 223, 'ISBN 978-94-007-5949-7')
ON CONFLICT DO NOTHING;

INSERT INTO book_author (id, book_id, author_id)
VALUES (uuid_generate_v5(uuid_ns_oid(), '1'), '802ae788-4c31-4d92-9055-bfcc6e9cfc5c',
				'2dabc86c-fc3a-58d3-b199-7386415ae67d'),
			 (uuid_generate_v5(uuid_ns_oid(), '2'), 'd8e483b0-eb6e-4c77-a100-d84e49bb35fc',
				'0b2041fc-002f-5734-aa7b-4d1eb8661c59'),
			 (uuid_generate_v5(uuid_ns_oid(), '3'), 'adbed02c-78ff-4de9-aab2-c086f9a3ec39',
				'6e5cfc16-54ed-56ba-9a59-4fe5761f6c2c'),
			 (uuid_generate_v5(uuid_ns_oid(), '4'), 'adbed02c-78ff-4de9-aab2-c086f9a3ec39',
				'6e5cfc16-54ed-56ba-9a59-4fe5761f6c2c')
ON CONFLICT DO NOTHING;

INSERT INTO book_genre (id, book_id, genre_id)
VALUES (uuid_generate_v5(uuid_ns_oid(), '1'), '802ae788-4c31-4d92-9055-bfcc6e9cfc5c',
				'ee1fd24c-786b-59c4-a712-4428ee65b61d'),
			 (uuid_generate_v5(uuid_ns_oid(), '2'), 'd8e483b0-eb6e-4c77-a100-d84e49bb35fc',
				'52b18bcc-3a12-518b-8ee2-22132652a82c'),
			 (uuid_generate_v5(uuid_ns_oid(), '3'), 'adbed02c-78ff-4de9-aab2-c086f9a3ec39',
				'8bb754ba-fb77-5a41-acc0-56fd6de7303c'),
			 (uuid_generate_v5(uuid_ns_oid(), '4'), 'adbed02c-78ff-4de9-aab2-c086f9a3ec39',
				'52b18bcc-3a12-518b-8ee2-22132652a82c')
ON CONFLICT DO NOTHING;
