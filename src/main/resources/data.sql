delete from communities;
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text
  ) values (
    '01', 'Corner Shop',
    1, '#cc7979',
    1, '#d8c286',
    1, '#f5dfd5ff',
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
  main_text, links_text, info_text, promo_text, approved
  ) values (
    '02', 'Sarah Resturant',
    2, '#be1313ff',
    2, '#ae0fd6ff',
    2, '#4a2d80ff',
    'A small Restraunt with the best food in town!',
    '-links text-',
    '-info text-',
    '-promo text-',
    1
  );


/* Google Gemini created examples */

insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text
) values (
  '03', 'The Old Library',
  1, '#5D4037',       /* Dark Brown Roof */
  1, '#F5F5DC',       /* Beige Base */
  1, '#8D6E63',       /* Brown Foreground */
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
  1, '#263238',       /* Dark Grey Roof */
  2, '#ECEFF1',       /* Light Grey Base */
  2, '#37474F',       /* Slate Foreground */
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
  2, '#33691E',       /* Dark Green Roof */
  1, '#8D6E63',       /* Earthy Base */
  1, '#558B2F',       /* Light Green Foreground */
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
  main_text, links_text, info_text, promo_text
) values (
  '06', 'Sweet Treats Bakery',
  2, '#E6B8B7',       /* Pinkish Roof */
  1, '#FFF8E1',       /* Cream Base */
  2, '#D7CCC8',       /* Light Brown Foreground */
  'Freshly baked bread, cakes, and pastries every morning.',
  'www.sweettreats.com',
  'Open 6am - 4pm daily',
  'Half-price croissants after 3pm!'  
);

insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved
) values (
  '07', 'PowerLift Gym',
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

/* Example 8: Primary School */
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved
) values (
  '0000', 'Sunnydale Primary',
  2, '#B71C1C',       /* Red Brick Roof */
  2, '#FFCC80',       /* Orange/Brick Base */
  2, '#3E2723',       /* Dark Brown Foreground */
  'Educating the next generation of community leaders.',
  'www.sunnydale.edu',
  'Office: 8am - 3:30pm',
  'Enrollment open for Fall 2025',
  1
);

/* Example 9: Pet Shop */
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved
) values (
  '0001', 'Paws & Claws',
  1, '#0288D1',       /* Bright Blue Roof */
  1, '#E1F5FE',       /* Light Blue Base */
  1, '#0277BD',       /* Deep Blue Foreground */
  'Everything you need for your furry, feathered, or scaled friends.',
  'Social: @PawsClawsShop',
  'Grooming services available',
  'Buy 1 get 1 free on dog treats',
  1
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

-- Streets

insert into streets (name, address, description) 
values ('Main Street', NULL, '');

insert into streets (name, address, description) 
values ('Food & Drink', '00', 'A home for all retaurants, takeaways, cafes, and more.');
