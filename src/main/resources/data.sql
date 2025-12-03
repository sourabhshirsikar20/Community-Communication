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
  main_text, links_text, info_text, promo_text, approved,
  created_date, approved
) values (
  '01', 'Corner Shop',
  1, '#cc7979',
  1, '#d8c286',
  1, '#f5dfd5ff',
  'Your local corner shop, here for all everyday items!',
  'www.cornershop.com',
  'Open 7am - 10pm daily',
  'Grand opening sale - 20% off!',
  1,,
    1
  NOW()
);

-- Block 2: Created 1 WEEK AGO (~175 days left)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '0200', 'Sarah Restaurant',
  2, '#be1313ff',
  2, '#ae0fd6ff',
  2, '#4a2d80ff',
  'A small restaurant with the best food in town!',
  'www.sarahrestaurant.com',
  'Reservations: 555-1234',
  'Try our new dinner menu!',
  1,
  DATE_SUB(NOW(), INTERVAL 1 WEEK)
);

-- Block 3: Created 1 MONTH AGO (~152 days left)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '03', 'The Old Library',
  1, '#5D4037',
  1, '#F5F5DC',
  1, '#8D6E63',
  'A quiet place to study and read books from our vast collection.',
  'www.library.org',
  'Open 9am - 5pm Mon-Sat',
  'Free membership this month!',
  1,
  DATE_SUB(NOW(), INTERVAL 1 MONTH)
);

-- Block 4: Created 2 MONTHS AGO (~121 days left)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '04', 'Tech Startups Hub',
  1, '#263238',
  2, '#ECEFF1',
  2, '#37474F',
  'Co-working space for local tech enthusiasts and startups.',
  'www.techhub.io',
  'Contact: admin@techhub.io',
  'Desks available from $10/day',
  1,
  DATE_SUB(NOW(), INTERVAL 2 MONTH)
);

-- Block 5: Created 3 MONTHS AGO (~91 days left - HALFWAY)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '05', 'Green Gardens',
  2, '#33691E',
  1, '#8D6E63',
  1, '#558B2F',
  'Fresh vegetables and flowers grown by the community, for the community.',
  'Social: @GreenGardens',
  'Volunteers welcome on weekends',
  'Fresh Carrots available now',
  1,
  DATE_SUB(NOW(), INTERVAL 3 MONTH)
);

-- Block 6: Created 4 MONTHS AGO (~61 days left)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date, approved
) values (
  '0201', 'Sweet Treats Bakery',
  2, '#E6B8B7',
  1, '#FFF8E1',
  2, '#D7CCC8',
  'Freshly baked bread, cakes, and pastries every morning.',
  'www.sweettreats.com',
  'Open 6am - 4pm daily',
  'Half-price croissants after 3pm!',
  1,
  DATE_SUB(NOW(), INTERVAL 4 MONTH)
);

-- Block 7: Created 5 MONTHS AGO (~30 days left - EXPIRING SOON!)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
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
  1,
  DATE_SUB(NOW(), INTERVAL 5 MONTH)
);

-- Block 8: Created 5 MONTHS, 3 WEEKS AGO (~7 days left - CRITICAL!)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '07', 'Sunnydale School',
  2, '#B71C1C',
  2, '#FFCC80',
  2, '#3E2723',
  'Educating the next generation of community leaders.',
  'www.sunnydale.edu',
  'Office: 8am - 3:30pm',
  'Enrollment open for Fall 2025',
  1,
  DATE_SUB(DATE_SUB(NOW(), INTERVAL 5 MONTH), INTERVAL 21 DAY)
);

-- Block 9: Created EXACTLY 6 MONTHS AGO (Expires TODAY - shows hours/minutes)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date, approved
) values (
  '08', 'Paws & Claws Pet Shop',
  1, '#0288D1',
  1, '#E1F5FE',
  1, '#0277BD',
  'Everything you need for your furry friends.',
  'Social: @PawsClawsShop',
  'Grooming services available',
  'Buy 1 get 1 free on dog treats',
  1,
  DATE_ADD(DATE_SUB(NOW(), INTERVAL 6 MONTH), INTERVAL 2 HOUR)
);

-- Block 10: Created 7 MONTHS AGO (EXPIRED - Red warning should show)
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '09', 'Classic Bookstore',
  1, '#FF0000',
  1, '#CCCCCC',
  1, '#888888',
  'A cosy bookstore with rare and vintage books for collectors.',
  'www.expired.com',
  'This listing is no longer active',
  'Please contact to renew',
  1,
  DATE_SUB(NOW(), INTERVAL 7 MONTH)
);

-- Block 11: Created 8 MONTHS AGO (EXPIRED - Red warning)
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '10', 'Riverside Community Center',
  2, '#8B0000',
  2, '#A9A9A9',
  2, '#696969',
  'A gathering place for community events, classes, and activities.',
  'www.oldcenter.com',
  'Expired listing',
  'No longer active',
  1,
  DATE_SUB(NOW(), INTERVAL 8 MONTH)
);

-- Block 12: Created 10 DAYS AGO (~172 days left)
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '11', 'Fresh Market',
  2, '#4CAF50',
  1, '#E8F5E9',
  2, '#2E7D32',
  'Farm-fresh produce delivered daily.',
  'www.freshmarket.local',
  'Open 7am - 7pm',
  'Organic vegetables 30% off',
  1,
  DATE_SUB(NOW(), INTERVAL 10 DAY)
);

-- Block 13: Created 2.5 MONTHS AGO (~106 days left)
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '12', 'Downtown Art Gallery',
  1, '#9C27B0',
  2, '#F3E5F5',
  1, '#6A1B9A',
  'Showcasing local artists and their incredible work.',
  'www.artgallery.local',
  'Tue-Sun 10am - 6pm',
  'New exhibition opening soon!',
  1,
  DATE_SUB(DATE_SUB(NOW(), INTERVAL 2 MONTH), INTERVAL 15 DAY)
);

-- Block 14: Created 4.5 MONTHS AGO (~45 days left)
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '13', 'The Coffee House',
  2, '#5D4037',
  1, '#EFEBE9',
  2, '#3E2723',
  'Artisan coffee and cosy atmosphere.',
  'www.coffeehouse.local',
  'Open 6am - 10pm',
  'Latte of the day: $3.50',
  1,
  DATE_SUB(DATE_SUB(NOW(), INTERVAL 4 MONTH), INTERVAL 15 DAY)
);

-- Block 15: Created 1 YEAR AGO (LONG EXPIRED)
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '14', 'Main Street Boutique',
  1, '#000000',
  1, '#424242',
  1, '#212121',
  'Fashion boutique featuring local designers and unique clothing.',
  'www.abandoned.com',
  'No longer in business',
  'Contact owner',
  1,
  DATE_SUB(NOW(), INTERVAL 12 MONTH)
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
