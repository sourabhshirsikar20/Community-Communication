delete from communities;

insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text
  ) values (
    '00', 'Corner Shop',
    0, '#cc7979',
    0, '#d8c286',
    0, '#f5dfd5ff',
    'Your local corner shop, here for all everyday items! ...',
    '-links text-',
    '-info text-',
    '-promo text-'
  );

  insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text
  ) values (
    '01', 'Sarah Resturant',
    0, '#be1313ff',
    0, '#3f3417ff',
    0, '#80472dff',
    'A small Restraunt with the best food in town!',
    '-links text-',
    '-info text-',
    '-promo text-'
  );

-- Security

insert into community_users (name, password, enabled) -- password is admin
values ('owner', '$2a$12$zKMHlbOC7UXOLCdq5ZibC.ANCzcxgpoGbode97Dc2Fi1zakG2fP6O', true);

insert into community_users (name, password, enabled) -- password is admin
values ('manager', '$2a$12$zKMHlbOC7UXOLCdq5ZibC.ANCzcxgpoGbode97Dc2Fi1zakG2fP6O', true);

insert into community_users (name, password, enabled) -- password is user
values ('user', '$2a$12$J1s9zHrJiu9VKV8J7FFXJ.U0ArVGE.9h3R94.a0OGx7a7ast0bcYi', true);

insert into community_roles (role, name) values (0, 'OWNER');
insert into community_roles (role, name) values (1, 'MANAGER');
insert into community_roles (role, name) values (2, 'USER');

insert into community_security (name, role) values ('owner', 0);
insert into community_security (name, role) values ('manager', 1);
insert into community_security (name, role) values ('manager', 2);
insert into community_security (name, role) values ('user', 2);