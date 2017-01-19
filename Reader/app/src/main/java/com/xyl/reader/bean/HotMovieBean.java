package com.xyl.reader.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.xyl.reader.bean.movieChild.ImagesBean;
import com.xyl.reader.bean.movieChild.RatingBean;
import com.xyl.reader.http.ParamNames;

import java.io.Serializable;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-01-17
 * Time: 11:25
 * Company: zx
 * Description:
 * FIXME
 */


public class HotMovieBean  extends BaseObservable implements Serializable{

    @ParamNames("count")
    private int count;
    @ParamNames("start")
    private int start;
    @ParamNames("total")
    private int total;
    @ParamNames("title")
    private String title;
    @ParamNames("subjects")
    private List<SubjectsBean> subjects;

    public void setCount(int count) {
        this.count = count;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    @Bindable
    public int getCount() {
        return count;
    }

    @Bindable
    public int getStart() {
        return start;
    }

    @Bindable
    public int getTotal() {
        return total;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public static class SubjectsBean {
        /**
         * rating : {"max":10,"average":6.9,"stars":"35","min":0}
         * genres : ["冒险","科幻"]
         * title : 太空旅客
         * casts : [{"alt":"https://movie.douban.com/celebrity/1022616/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/1358747052.41.jpg","large":"http://img7.doubanio.com/img/celebrity/large/1358747052.41.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/1358747052.41.jpg"},"name":"詹妮弗·劳伦斯","id":"1022616"},{"alt":"https://movie.douban.com/celebrity/1017967/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/1408271589.94.jpg","large":"http://img7.doubanio.com/img/celebrity/large/1408271589.94.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/1408271589.94.jpg"},"name":"克里斯·普拉特","id":"1017967"},{"alt":"https://movie.douban.com/celebrity/1004566/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/21073.jpg","large":"http://img7.doubanio.com/img/celebrity/large/21073.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/21073.jpg"},"name":"麦克·辛","id":"1004566"}]
         * collect_count : 27800
         * original_title : Passengers
         * subtype : movie
         * directors : [{"alt":"https://movie.douban.com/celebrity/1299674/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/38694.jpg","large":"http://img7.doubanio.com/img/celebrity/large/38694.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/38694.jpg"},"name":"莫滕·泰杜姆","id":"1299674"}]
         * year : 2016
         * images : {"small":"http://img3.doubanio.com/view/movie_poster_cover/ipst/public/p2410576676.jpg","large":"http://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2410576676.jpg","medium":"http://img3.doubanio.com/view/movie_poster_cover/spst/public/p2410576676.jpg"}
         * alt : https://movie.douban.com/subject/3434070/
         * id : 3434070
         */

        private RatingBean rating;
        private String title;
        private int collect_count;
        private String original_title;
        private String subtype;
        private String year;
        private ImagesBean images;
        private String alt;
        private String id;
        private List<String> genres;
        private List<CastsBean> casts;
        private List<DirectorsBean> directors;

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public void setCasts(List<CastsBean> casts) {
            this.casts = casts;
        }

        public void setDirectors(List<DirectorsBean> directors) {
            this.directors = directors;
        }

        public RatingBean getRating() {
            return rating;
        }

        public String getTitle() {
            return title;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public String getYear() {
            return year;
        }

        public ImagesBean getImages() {
            return images;
        }

        public String getAlt() {
            return alt;
        }

        public String getId() {
            return id;
        }

        public List<String> getGenres() {
            return genres;
        }

        public List<CastsBean> getCasts() {
            return casts;
        }

        public List<DirectorsBean> getDirectors() {
            return directors;
        }


        public static class CastsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1022616/
             * avatars : {"small":"http://img7.doubanio.com/img/celebrity/small/1358747052.41.jpg","large":"http://img7.doubanio.com/img/celebrity/large/1358747052.41.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/1358747052.41.jpg"}
             * name : 詹妮弗·劳伦斯
             * id : 1022616
             */

            private String alt;
            private AvatarsBean avatars;
            private String name;
            private String id;

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public void setAvatars(AvatarsBean avatars) {
                this.avatars = avatars;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAlt() {
                return alt;
            }

            public AvatarsBean getAvatars() {
                return avatars;
            }

            public String getName() {
                return name;
            }

            public String getId() {
                return id;
            }

            public static class AvatarsBean {
                /**
                 * small : http://img7.doubanio.com/img/celebrity/small/1358747052.41.jpg
                 * large : http://img7.doubanio.com/img/celebrity/large/1358747052.41.jpg
                 * medium : http://img7.doubanio.com/img/celebrity/medium/1358747052.41.jpg
                 */

                private String small;
                private String large;
                private String medium;

                public void setSmall(String small) {
                    this.small = small;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }

                public String getSmall() {
                    return small;
                }

                public String getLarge() {
                    return large;
                }

                public String getMedium() {
                    return medium;
                }
            }
        }

        public static class DirectorsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1299674/
             * avatars : {"small":"http://img7.doubanio.com/img/celebrity/small/38694.jpg","large":"http://img7.doubanio.com/img/celebrity/large/38694.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/38694.jpg"}
             * name : 莫滕·泰杜姆
             * id : 1299674
             */

            private String alt;
            private AvatarsBean avatars;
            private String name;
            private String id;

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public void setAvatars(AvatarsBean avatars) {
                this.avatars = avatars;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAlt() {
                return alt;
            }

            public AvatarsBean getAvatars() {
                return avatars;
            }

            public String getName() {
                return name;
            }

            public String getId() {
                return id;
            }

            public static class AvatarsBean {
                /**
                 * small : http://img7.doubanio.com/img/celebrity/small/38694.jpg
                 * large : http://img7.doubanio.com/img/celebrity/large/38694.jpg
                 * medium : http://img7.doubanio.com/img/celebrity/medium/38694.jpg
                 */

                private String small;
                private String large;
                private String medium;

                public void setSmall(String small) {
                    this.small = small;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }

                public String getSmall() {
                    return small;
                }

                public String getLarge() {
                    return large;
                }

                public String getMedium() {
                    return medium;
                }
            }
        }
    }
}
