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

delete from organisationinfo;
insert into organisationinfo(
MainText, Links, Info, Promo
) values
('Example Main Text', 'www.google.com', 'ExtraInfo', 'Promo');
insert into organisationinfo(
MainText, Links, Info, Promo
) values
('Example Main Text 2', 'www.youtube.com', 'ExtraInfo', 'Promo');
insert into organisationinfo(
MainText, Links, Info, Promo
) values
('Example Main Text 3', 'www.facebook.com', 'ExtraInfo', 'Promo');
insert into organisationinfo(
MainText, Links, Info, Promo
) values
('Example Main Text 4', 'www.reddit.com', 'ExtraInfo', 'Promo');