create table if not exists betslips
(
    betslip_id         bigint         not null primary key,
    player_id          varchar(64)    null,
    wager_amount       decimal(19, 2) null,
    return_amount      decimal(19, 2) null,
    odds               decimal(19, 2) null,
    placed_at          date      null,
    settled_at         date      null,
    status             varchar(64)    null
);

create table if not exists player_profit
(
    player_id          varchar(64)       not null primary key,
    total_profit       decimal(19, 2)    null
);