ALTER TABLE participants
ADD COLUMN id_activities UUID;

ALTER TABLE participants
ADD CONSTRAINT fk_id_activities
FOREIGN KEY (id_activities) REFERENCES activities(id);