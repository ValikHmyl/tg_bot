CREATE TABLE if not exists settings(
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(20)
);

CREATE TABLE if not exists settings_value_mapping(
    subscriber_id BIGINT,
    setting_id BIGINT,
    value VARCHAR(50),
    PRIMARY KEY (subscriber_id, setting_id),
    CONSTRAINT fk_sub FOREIGN KEY(subscriber_id) REFERENCES subscriber(id),
    CONSTRAINT fk_setting FOREIGN KEY(setting_id) REFERENCES settings(id)
);

INSERT INTO settings(type) VALUES ('locale')
;