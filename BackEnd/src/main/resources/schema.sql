CREATE TABLE IF NOT EXISTS client (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    document VARCHAR(255) UNIQUE,
    document_type VARCHAR(255),
    plan_type VARCHAR(255),
    balance REAL,
    credit_limit REAL,
    active BOOL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS conversation (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id UUID REFERENCES client(id),
    recipient_id VARCHAR(255),
    recipient_name VARCHAR(255),
    recipient_phone_number VARCHAR(255),
    last_message_content TEXT,
    last_message_time TIMESTAMP,
    unread_count INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS message (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    conversation_id UUID REFERENCES conversation(id),
    sender_id VARCHAR(255),
    sender_type VARCHAR(255),     -- client | user
    recipient_id VARCHAR(255),
    content TEXT,
    priority VARCHAR(255),        -- normal | urgent
    status VARCHAR(255),          -- queued | processing | sent | delivered | read | failed
    cost NUMERIC(10,2),           -- 0.25 normal, 0.50 urgente
    created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE IF NOT EXISTS transaction (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id UUID REFERENCES client(id),
    message_id UUID REFERENCES message(id),  -- nulo em recargas do admin
    type VARCHAR(255),                       -- debit | credit
    amount NUMERIC(12,2),
    created_at TIMESTAMP DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_message_conversation ON message (conversation_id, created_at);