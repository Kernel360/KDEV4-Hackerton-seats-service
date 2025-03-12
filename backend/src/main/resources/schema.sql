CREATE TABLE IF NOT EXISTS seat (
    id BIGINT PRIMARY KEY,
    name ENUM('좌석 A', '좌석 B', '좌석 C', '좌석 D')
);