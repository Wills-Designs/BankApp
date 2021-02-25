create table user_roles (
                            role_id		serial,
                            role_name	varchar(25) unique not null,

                            constraint user_roles_pk
                                primary key (role_id)
);

create table app_users (
                           user_id		serial,
                           username	varchar unique not null,
                           password	varchar	not null,
                           first_name	varchar not null,
                           last_name	varchar not null,
                           role_id		int not null,

                           constraint app_users_pk
                               primary key (user_id),

                           constraint app_user_role_fk
                               foreign key (role_id)
                                   references user_roles
);
COMMIT;

insert into user_roles (role_name)
VALUES ('ADMIN');

insert into user_roles (role_name)
VALUES('ACCOUNT_OWNER');

insert into user_roles (role_name)
VALUES('JOINT_USER');

create table accounts(
                         account_number serial not null,
                         current_balance numeric(15,2) default 0,
                         user_id int not null,
                         constraint account_pk primary key (account_number),
                         constraint app_users_fk foreign key (user_id)
                             references app_users
);

commit;

-- created trigger function to populate a new record in the
-- accounts table after a new user is inserted
create or replace function create_new_account()
returns trigger as
$$
begin
insert into accounts (user_id)
values (new.user_id);
return new;
end;
$$
language 'plpgsql';


create trigger create_acc_trigger
    after insert
    on app_users
    for each row
    execute procedure create_new_account();

commit;