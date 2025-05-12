import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay, Pagination, EffectFade, Navigation } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import 'swiper/css/effect-fade';

import bannerList from "../util/bannerList.js";
import { Link } from 'react-router-dom';

export default function HeroBanner() {
  const colors = ["bg-blue-500", "bg-purple-500", "bg-pink-500", "bg-teal-500"];

  return (
    <div className='py-2 rounded-md'>
      <Swiper
        grabCursor={true}
        autoplay={{
          delay: 4000,
          disableOnInteraction: false,
        }}
        navigation
        modules={[Pagination, EffectFade, Navigation, Autoplay]}
        pagination={{ clickable: true }}
        slidesPerView={1}
      >
        {bannerList.map((item, i) => (
          <SwiperSlide key={item.id}>
            <div className={`carousel-item rounded-md sm:h-[500px] h-96 ${colors[i % colors.length]} flex flex-col lg:flex-row items-center justify-between px-4 sm:px-8 lg:px-12`}>
  
  {/* Text Section */}
  <div className='w-full lg:w-1/2 text-white lg:pr-10'>
    <h3 className='text-xl sm:text-2xl font-semibold'>{item.title}</h3>
    <h1 className='text-3xl sm:text-5xl font-bold mt-2'>{item.subtitle}</h1>
    <p className='mt-4 text-sm sm:text-base'>{item.description}</p>
    <Link 
      className='mt-6 inline-block bg-black text-white py-2 px-4 rounded hover:bg-gray-800'
      to="/products"
    >
      Shop
    </Link>
  </div>

  {/* Image Section */}
  <div className='w-full lg:w-1/2 mt-6 lg:mt-0 flex justify-center'>
    <img src={item.image} alt={item.title} className='max-h-72 object-contain' />
  </div>
</div>

          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
}
