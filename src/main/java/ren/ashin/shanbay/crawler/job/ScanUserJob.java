package ren.ashin.shanbay.crawler.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @ClassName: ScanUserJob
 * @Description: 用户关联表扫描job
 * @author renzx
 * @date Apr 12, 2017
 */
public class ScanUserJob implements Job {
    private static final Logger LOG = Logger.getLogger(ScanUserJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date fireTime = context.getFireTime();
        exeTask(fireTime);
    }

    private void exeTask(Date fireTime) {
        LOG.info("用户表扫描执行中" + new DateTime(fireTime).toString("yyyy-MM-dd HH:mm:ss"));
        /*List<HqUser> userList = Lists.newArrayList();
        userList = hqUserDao.findUserByStatus(0L);
        LOG.debug("检测到未执行任务数量：" + userList.size());
        for (HqUser user : userList) {
            // 用户登录并获取当前的用户题目关联信息
            CookieStore userCookie = UserCookieCache.getInstance().updateCookieByUser(user);
            int pageNum = 1;
            List<HqCourse> courseList = Lists.newArrayList();
            List<HqCourse> allExistCourse = hqCourseDao.selectAllCourse();
            Set<String> courseUuidSet = Sets.newHashSet();
            Set<String> newCourseUuidSet = Sets.newHashSet();
            for (HqCourse course : allExistCourse) {
                courseUuidSet.add(course.getUuId());
            }
            while (true) {
                String url =
                        "http://www.hq88.com/lms/member/course/course_myJob?type=0&isStudyFinished=-1&page="
                                + pageNum;
                RequestConfig config = RequestConfig.custom().build();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setConfig(config);
                CloseableHttpClient closeableHttpClient =
                        HttpClients.custom().setDefaultCookieStore(userCookie).build();
                HttpResponse response = null;
                try {
                    response = closeableHttpClient.execute(httpPost);
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    try {
                        String htmlStr = EntityUtils.toString(entity);
                        Document d1 = Jsoup.parse(htmlStr);// 转换为Dom树
                        if (pageNum == 1) {
                            // 获取用户名
                            Element userName = d1.select("span.user-name").first();//
                            if (userName != null && !Strings.isNullOrEmpty(userName.text())) {
                                // 更新用户表该用户任务执行中，如果找不到直接设为失败
                                user.setStatus(1L);
                                user.setName(userName.text());
                                hqUserDao.updateUserStatusAndName(user);
                            } else {
                                user.setStatus(3L);
                                hqUserDao.updateUserStatus(user);
                                break;
                            }
                        }


                        // 获取第一页的课程信息
                        Element course =
                                d1.getElementsByAttributeValue("class",
                                        "course course-float clearfix").first();//
                        List<Element> allCourse = course.getElementsByTag("li");
                        if (allCourse.size() < 1) {
                            break;
                        }

                        for (Element element : allCourse) {
                            String name = element.getElementsByTag("h4").text();
                            String link =
                                    element.getElementsByTag("a").val("学习").last().attr("href");
                            String uuid = StringUtils.substringBetween(link, "(0,'", "','");
                            if (uuid == null) {
                                uuid = StringUtils.substringBetween(link, "('", "');");
                            }
                            if (uuid == null) {
                                LOG.debug("未取到uuid当前的link值：" + link + "\r\n");
                                LOG.debug("当前的element值：" + element);
                                continue;
                            }
                            newCourseUuidSet.add(uuid);
                            Long type = 0L;
                            Element examEle =
                                    element.getElementsByAttributeValue("class", "btn-box").last()
                                            .getElementsByTag("a").val("考试").last();
                            if (examEle == null
                                    || (examEle != null && !examEle.text().equals("考试"))) {
                                type = 1L;
                            }
                            if (!courseUuidSet.contains(uuid)) {
                                HqCourse hqCourse = new HqCourse();
                                hqCourse.setUuId(uuid);
                                hqCourse.setName(name);
                                hqCourse.setType(type);
                                hqCourse.setUpdateTime(new Date());
                                hqCourse.setCreateTime(new Date());
                                courseList.add(hqCourse);
                            }
                        }
                        // System.out.println(d1);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                pageNum++;
            }
            // 将当前取到的课程数据保存到mysql中
            for (HqCourse hqCourse : courseList) {
                hqCourseDao.listInsert(hqCourse);
            }
            
            // 取回课程数据，用于保存用户课程关联关系
            allExistCourse = hqCourseDao.selectAllCourse();
            // 组建一个临时的uuid-id的Map
            Map<String,Long> tmpMap = Maps.newHashMap();
            for (HqCourse course : allExistCourse) {
                tmpMap.put(course.getUuId(), course.getId());
            }
            hqUcRelationDao.deleteRelationByUserId(user.getId());
            for (String uuId : newCourseUuidSet) {
                HqUcRelation hqUcRelation = new HqUcRelation();
                hqUcRelation.setUserId(user.getId());
                hqUcRelation.setCourseId(tmpMap.get(uuId));
                hqUcRelation.setStatus(0L);
                hqUcRelation.setCreateTime(new Date());
                hqUcRelation.setUpdateTime(new Date());
                hqUcRelationDao.listInsert(hqUcRelation);
            }
            
            // 更新用户状态
            if (user.getStatus() < 2) {
                user.setStatus(2L);
                hqUserDao.updateUserStatus(user);
            }
        }*/
    }

}
