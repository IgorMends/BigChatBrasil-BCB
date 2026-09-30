CREATE TABLE IF NOT EXISTS client (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    document VARCHAR(255),
    document_type VARCHAR(255),
    planType VARCHAR(255),
    balance REAL,
    limit REAL,
    active BOOL
);