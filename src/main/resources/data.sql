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

insert into logins (login_name, password, enabled) -- password: 'manager'
values ('manager', '$2a$10$uXkdCPrB3Adlpw0H2EUWWeoMVWcGwtDroMR.YeZ9BW13Za9fe5chu', true);

insert into logins (login_name, password, enabled) -- password: 'shops'
values ('user', '$2a$10$1T51cvA5jk4d1uu/P9462.rVKfGXOxLB.olz79MpOpa5XYHHOCblS', true);

insert into roles (role_id, name) values (1, 'MANAGER');
insert into roles (role_id, name) values (2, 'USER');

insert into logins_roles (login_name, role_id) values ('manager', 1);
insert into logins_roles (login_name, role_id) values ('manager', 2);
insert into logins_roles (login_name, role_id) values ('user', 2);