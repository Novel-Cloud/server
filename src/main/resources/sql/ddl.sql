create table novel.member
(
    id       bigint auto_increment
        primary key,
    email    varchar(255) not null,
    nickname varchar(255) not null,
    picture  varchar(255) not null,
    constraint UK_mbmcqelty0fbrvxp1q58dn57t
        unique (email)
);

create table novel.abbreviation
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime(6)  not null,
    last_modified_date datetime(6)  not null,
    content            varchar(100) not null,
    sequence           int          not null,
    writer_id          bigint       not null,
    constraint FKaf9n4s2d3gkvlj3oj24ca6e2m
        foreign key (writer_id) references novel.member (id)
);

create table novel.artwork
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime(6)   not null,
    last_modified_date datetime(6)   not null,
    artwork_type       varchar(255)  not null,
    content            varchar(3000) not null,
    title              varchar(255)  not null,
    view               bigint        not null,
    writer_id          bigint        not null,
    thumbnail          varchar(200)  null,
    constraint FK4hi1ic3kldro4g44eklpsrq0l
        foreign key (writer_id) references novel.member (id)
);

create table novel.attach_file
(
    id                      bigint auto_increment
        primary key,
    created_date            datetime(6)  not null,
    last_modified_date      datetime(6)  not null,
    file_name               varchar(200) not null,
    file_path               varchar(200) not null,
    file_size               bigint       not null,
    artwork_id              bigint       not null,
    mutable_attach_files_id bigint       not null,
    file_uid_name           varchar(200) not null,
    attach_file_type        varchar(255) not null,
    constraint FKbnpka2j28v795sf6of61okbwq
        foreign key (mutable_attach_files_id) references novel.artwork (id),
    constraint FKh4jwlf53sw47bjciplk8pik4a
        foreign key (artwork_id) references novel.artwork (id)
);

create table novel.bookmark
(
    id                   bigint auto_increment
        primary key,
    created_date         datetime(6) not null,
    last_modified_date   datetime(6) not null,
    artwork_id           bigint      not null,
    member_id            bigint      not null,
    mutable_bookmarks_id bigint      not null,
    constraint FK1mlegl78k27w12vl7gu53tan1
        foreign key (mutable_bookmarks_id) references novel.artwork (id),
    constraint FK3mn105lijv9xkr0sicmkxvp9b
        foreign key (artwork_id) references novel.artwork (id),
    constraint FK92elpxdllryxi5cov1b41fr0
        foreign key (member_id) references novel.member (id)
);

create table novel.comment
(
    id                   bigint auto_increment
        primary key,
    created_date         datetime(6)   not null,
    last_modified_date   datetime(6)   not null,
    content              varchar(3000) null,
    artwork_id           bigint        not null,
    writer_id            bigint        not null,
    mutable_comments_id  bigint        not null,
    parent_id            bigint        null,
    mutable_children_id  bigint        null,
    deleted              varchar(255)  not null,
    children_comments_id bigint        null,
    constraint FK5au6krj8diwmav7ugiwokgmpf
        foreign key (mutable_comments_id) references novel.artwork (id),
    constraint FKde3rfu96lep00br5ov0mdieyt
        foreign key (parent_id) references novel.comment (id),
    constraint FKdv2x2ac1m8exft0dps6b9veq5
        foreign key (mutable_children_id) references novel.comment (id),
    constraint FKjqppol81l9gsfxdj3ndq0n8c0
        foreign key (children_comments_id) references novel.comment (id),
    constraint FKo3w9tfkwnlgs0al9v29by9ho6
        foreign key (writer_id) references novel.member (id),
    constraint FKsl1tjkvy4e52evxrmr1q4t4t6
        foreign key (artwork_id) references novel.artwork (id)
);

create table novel.tag
(
    id          bigint auto_increment
        primary key,
    content     varchar(100) not null,
    writer_id   bigint       not null,
    usage_count bigint       not null,
    constraint tag_content_uk
        unique (content),
    constraint FKll86lknsobwl65qt2721ycfhe
        foreign key (writer_id) references novel.member (id)
);

create table novel.artwork_tag_assoc
(
    artwork_id bigint not null,
    tag_id     bigint not null,
    primary key (artwork_id, tag_id),
    constraint FKjpodl43pfhar79ljp02ev24bi
        foreign key (tag_id) references novel.tag (id),
    constraint FKk6oafmhoymmpgt2rmp0vpm2br
        foreign key (artwork_id) references novel.artwork (id)
);

create table novel.temporary_artwork
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime(6)    not null,
    last_modified_date datetime(6)    not null,
    content            varchar(10000) not null,
    writer_id          bigint         not null,
    constraint FKfedgcojdpnr2cjdnp0jl0o3ba
        foreign key (writer_id) references novel.member (id)
);

