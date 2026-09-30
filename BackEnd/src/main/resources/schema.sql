CREATE TABLE IF NOT EXISTS client (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    document VARCHAR(255),
    document_type VARCHAR(255),
    plan_type VARCHAR(255),
    balance REAL,
    credit_limit REAL,
    active BOOL
);