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