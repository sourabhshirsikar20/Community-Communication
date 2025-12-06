-- Clean old data
DELETE FROM logins_roles;
DELETE FROM logins_roles;
DELETE FROM logins;
DELETE FROM roles;
DELETE FROM communities;
DELETE FROM streets;

-- Insert communities

insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved
  ) values (
    '01', 'Corner Shop',
    1, '#cc7979',
    1, '#d8c286',
    1, '#f5dfd5ff',
    'Your local corner shop, here for all everyday items! ...',
    '-links text-',
    '-info text-',
    '-promo text-',
    1
  );

insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved
  ) values (
    '020000', 'Sarah Resturant',
    2, '#be1313ff',
    2, '#ae0fd6ff',
    2, '#4a2d80ff',
    'A small Restraunt with the best food in town!',
    '-links text-',
    '-info text-',
    '-promo text-',
    1
  );

insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text
) values (
  '03', 'The Old Library',
  1, '#5D4037',
  1, '#F5F5DC',
  1, '#8D6E63',
  'A quiet place to study and read books from our vast collection.',
  'www.library.org',
  'Open 9am - 5pm',
  'Free membership this month!'
);

insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved
) values (
  '04', 'Tech Startups',
  1, '#263238',
  2, '#ECEFF1',
  2, '#37474F',
  'Co-working space for local tech enthusiasts and startups.',
  'www.techhub.io',
  'Contact: admin@techhub.io',
  'Desks available from $10/day',
  1
);

insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved
) values (
  '05', 'Green Gardens',
  2, '#33691E',
  1, '#8D6E63',
  1, '#558B2F',
  'Fresh vegetables and flowers grown by the community, for the community.',
  'Social: @GreenGardens',
  'Volunteers welcome on weekends',
  'Fresh Carrots available now',
  1
);

insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved
) values (
  '0201', 'Sweet Treats Bakery',
  2, '#E6B8B7',
  1, '#FFF8E1',
  2, '#D7CCC8',
  'Freshly baked bread, cakes, and pastries every morning.',
  'www.sweettreats.com',
  'Open 6am - 4pm daily',
  'Half-price croissants after 3pm!',
  1 
);

insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved
) values (
  '06', 'PowerLift Gym',
  1, '#424242',       /* Dark Grey Roof */
  2, '#B0BEC5',       /* Steel Blue/Grey Base */
  1, '#263238',       /* Charcoal Foreground */
  '{"serif":false,"italic":true,"weight":450,"width":85}', '#e2ac6f',
  'Your local spot for fitness and strength training.',
  'www.powerlift.local',
  '24/7 Access for members',
  'Join now for $0 initiation fee',
  1
);

insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved
) values (
  '07', 'Sunnydale Primary',
  2, '#B71C1C',
  2, '#FFCC80',
  2, '#3E2723',
  'Educating the next generation of community leaders.',
  'www.sunnydale.edu',
  'Office: 8am - 3:30pm',
  'Enrollment open for Fall 2025',
  1
);

insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved
) values (
  '08', 'Paws & Claws',
  1, '#0288D1',
  1, '#E1F5FE',
  1, '#0277BD',
  'Everything you need for your furry, feathered, or scaled friends.',
  'Social: @PawsClawsShop',
  'Grooming services available',
  'Buy 1 get 1 free on dog treats',
  1
);

-- SECURITY SETUP

-- Roles
INSERT INTO roles (role_id, name) VALUES (1, 'MANAGER');
INSERT INTO roles (role_id, name) VALUES (2, 'USER');

-- Users
INSERT INTO logins (login_name, password, enabled)
VALUES ('manager', '$2a$10$uXkdCPrB3Adlpw0H2EUWWeoMVWcGwtDroMR.YeZ9BW13Za9fe5chu', true);

INSERT INTO logins (login_name, password, enabled)
VALUES ('user', '$2a$10$1T51cvA5jk4d1uu/P9462.rVKfGXOxLB.olz79MpOpa5XYHHOCblS', true);

-- Role mapping
insert into logins_roles (login_name, role_id) values ('manager', 1);
insert into logins_roles (login_name, role_id) values ('manager', 2);
insert into logins_roles (login_name, role_id) values ('user', 2);

-- Streets

insert into streets (name, address, description) 
values ('Main Street', 'root', 'Everything starts here, explore your community!');

insert into streets (name, address, description) 
values ('New Blocks', '00', 'A place to find newly-added blocks');

insert into streets (name, address, description) 
values ('Food & Drink', '02', 'A home for all retaurants, takeaways, cafes, and more');

insert into streets (name, address, description) 
values ('Restaurants', '0200', '');

insert into streets (name, address, description) 
values ('Fast Food', '0202', '');

insert into streets (name, address, description) 
values ('Events', '09', 'Find all local events taking place soon here');