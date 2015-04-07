
# coding: utf-8

# In[1]:

from sklearn import datasets


# In[2]:

train_data = datasets.load_files('url_train', description=None, categories=None, load_content=True, shuffle=True, encoding='utf-8', charset=None, charset_error=None, decode_error='ignore', random_state=0)


# In[3]:

train_data.target_names


# In[4]:

len(train_data)


# In[5]:

from sklearn.feature_extraction.text import CountVectorizer


# In[6]:

count_vect = CountVectorizer()
test_cv = count_vect.fit_transform(train_data.data)
test_cv.shape


# In[7]:

from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import Pipeline
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.linear_model import SGDClassifier
import numpy as np


clf_huber = Pipeline([('vect', CountVectorizer()),
                     ('tfidf', TfidfTransformer()),
                     ('clf', SGDClassifier(loss='modified_huber',penalty='l2',alpha = 1e-3, n_iter=5))])
clf_hinge = Pipeline([('vect', CountVectorizer()),
                     ('tfidf', TfidfTransformer()),
                     ('clf', SGDClassifier(loss='hinge',penalty='l2',alpha = 1e-3, n_iter=5))])
clf_huber_w_ngram = Pipeline([('vect', CountVectorizer(ngram_range=(1,2))),
                     ('tfidf', TfidfTransformer()),
                     ('clf', SGDClassifier(loss='hinge',penalty='l2',alpha = 0.01, n_iter=5))])
clf_multinom_w_ngram = Pipeline([('vect', CountVectorizer(ngram_range=(1,2))),
                     ('tfidf', TfidfTransformer()),
                     ('clf', MultinomialNB())])
clf_multinom = Pipeline([('vect', CountVectorizer()),
                     ('tfidf', TfidfTransformer()),
                     ('clf', MultinomialNB())])


# In[13]:

clf_huber = clf_huber.fit(train_data.data, train_data.target)
clf_hinge = clf_hinge.fit(train_data.data, train_data.target)
clf_huber_w_ngram = clf_huber_w_ngram.fit(train_data.data, train_data.target)
clf_multinom = clf_huber_w_ngram.fit(train_data.data, train_data.target)
clf_multinom_w_ngram = clf_huber_w_ngram.fit(train_data.data, train_data.target)


# In[14]:

test_data = datasets.load_files('url_test', description=None, categories=None, load_content=True, shuffle=True, encoding='utf-8', charset=None, charset_error=None, decode_error='ignore', random_state=0)


# In[15]:

predicted_hinge = clf_hinge.predict(test_data.data)
predicted_huber = clf_huber.predict(test_data.data)
predicted_huber_w_ngram = clf_huber_w_ngram.predict(test_data.data)
predicted_multinom = clf_multinom.predict(test_data.data)
predicted_multinom_w_ngram = clf_multinom_w_ngram.predict(test_data.data)


print 'huber: ',np.mean(predicted_huber == test_data.target)
print 'hinge: ',np.mean(predicted_hinge == test_data.target)
print 'huber w/ ngram: ',np.mean(predicted_huber_w_ngram == test_data.target)
print 'multinom: ',np.mean(predicted_multinom == test_data.target)
print 'multinom w/ ngram: ',np.mean(predicted_multinom_w_ngram == test_data.target)


# In[16]:

from sklearn import metrics
print 'HUBER:\n', (metrics.classification_report(test_data.target,predicted_huber,target_names = test_data.target_names))
print 'HINGE:\n', (metrics.classification_report(test_data.target,predicted_hinge,target_names = test_data.target_names))
print 'HINGE W/ NGRAM:\n', (metrics.classification_report(test_data.target,predicted_huber_w_ngram,target_names = test_data.target_names))
print 'MULTINOM:\n', (metrics.classification_report(test_data.target,predicted_multinom,target_names = test_data.target_names))
print 'MULTINOM W/ NGRAM:\n', (metrics.classification_report(test_data.target,predicted_multinom_w_ngram,target_names = test_data.target_names))


# In[124]:

doc1 = "Northwest Optimist Soccer: Staff PRESIDENT: If you have any questions regarding anything call one of the following numbers below. Dave Sargent: 495-5860 Email: jd.sargent@cox.net REGISTRAR: Dave Sargent: 495-5860 COMPETITIVE COORDINATOR: Dave Sargent: 495-5860 AGE GROUP COORDINATOR: If you have a question about what age group your child will play under call one of the following numbers: Under 6 Age Group Coordinator: Javier Lopez 440-2420 Under 8 Age Group Coordinator: Sean Satlow 371-1590 Under 10 Age Group Coordinator:Dave Sargent- 495-5860 Under 12-14-16 Coordinator: Dave Sargent 495-5860 Referee Assignor: Casey Smith 405-519-5694 Director of Academy Coaches:VACANT Director of†Coaches: VACANT HOTLINE: 405.495.5860 Click here to sign up for email and text updates. NW Optimist Soccer Club - Mailing Address - 4605 N Stanley - Oklahoma City, OK 73122 - (405) 495-5860"
doc2 = "Washington County Soccer Club | Home Contact Us Donate Multimedia Club Store Logo Goes Here About Us Club Information About Washington Coun... Club Philosophy Rescheduling games Latest WCSC Board Mee... Calendar of Events Board of Directors Mission Statement Bylaws of the WCSC Standing Resolutions ... Coaches List Spring 2... Practice Field Schedu... Maps & Directions Forms Donate History GCSA HISTORY WCSC MILESTONES BRUIN HIGH SCHOOL WCSC 1991-2001 WCSC 1978-1990 Photo Gallery Photo Submission Inst... Helpful Links Academy About FC Bartlesville... U9/U10 Boys Training Times and Lo... Schedules Game Coaches Registration Forms Competitive About FC Bartlesville... College Recruiting OPL Schedules Classic Schedules Code of Conduct FCB Coaching applicat... Team Pages Coaches and Trainers Wade Sturges Ryan Hunt Jordan Belong Justin Copeland David Critchley Mike Friend Allen Godwin Aaron Kuntz Justin Marshall Forms Recreational About WCSC Recreation... Schedules Forms BHS Bruin Boys JV & Varsi... Lady Bruin JV & Varsi... Forms Alumni Referees Ref Cheat Sheet Matri... Schedules Referee Payment Sched... Referee Assignments Clinics Referee Blog News & Events Calendar of Events Latest News ACADEMY + REGISTRATION ONLINE FCB Academy + Academy + is an additional program formed t ... ACADEMY + REGISTRATION ONLINE Feb 27, 2015 FALL LEAGUE CHAMPIONS WCSC would like to congratulate our league champions from this ... FALL LEAGUE CHAMPIONS Jan 27, 2015 FCB 99 GIRLS - INDOOR CHAMPS The FCB 99 Girls soccer team won the Soccer City U16 Competiti ... FCB 99 GIRLS - INDOOR CHAMPS Jan 14, 2015 Welcome to Washington County Soccer Club Welcome to Washington County Soccer Club (WCSC) located in Bartlesville Oklahoma. Established in 1978 as a nonprofit, WCSC is one of Oklahomaís original soccer clubs and has grown and flourished over the past 3 decades. Our geographical location allows us to give competitive and recreational soccer opportunities to the youth in areas of Northeastern Oklahoma and Southeastern Kansas. With having over 500 children playing in our soccer program each season, WCSC is committed to providing developmentally appropriate environments for all levels of players. We offer three distinct programs: Academy Soccer (ages 4-10), Competitive Soccer program (ages 11-18) and Recreational soccer (ages 11-18).† Coaches Blog Saturday, March 14th, 2015 Getting Players to Pay Attenti ... Saturday, March 7th, 2015 USSF Wants Small Sided Games Tuesday, March 3th, 2015 6 Reasons Parents Should Not W ... Tuesday, March 3th, 2015 See How the Adults Like It Friday, December 6th, 2013 Why Futsal? Friday, November 29th, 2013 Questions to Help Parents Eval ... Friday, November 22th, 2013 'Crazier than it's ever been' Friday, November 15th, 2013 Assistant Coaches: 'Be flexibl ... Friday, November 1th, 2013 Head coaches: 'Treat assistant ... Friday, October 25th, 2013 Getting the most out of assist ... Friday, October 18th, 2013 Ian McClurg on Youth Soccer Friday, October 11th, 2013 What does it take to be the be ... Friday, October 4th, 2013 Communication Between Coaches ... Friday, September 20th, 2013 Instilling respect, passion a ... Monday, September 16th, 2013 'Patience is crucial for coach ... Monday, September 9th, 2013 USA's No. 1 club aims to produ ... Friday, August 30th, 2013 Getting players to pay attenti ... Friday, August 23th, 2013 Tab Ramos: 'We want to play in ... Friday, August 16th, 2013 Sleep well, play well (The tee ... Friday, August 9th, 2013 Brad Friedel: World Class Keep ... Friday, August 9th, 2013 Keeper Qustions: Dressing, and ... Friday, August 2th, 2013 10 tips for the well-organized ... Friday, July 26th, 2013 Long, Hot Summer: Watch For He ... Friday, July 26th, 2013 Drink Up: Hydration Tips for S ... Monday, June 3th, 2013 Tryouts: Coping with Cuts Monday, April 8th, 2013 Mental Toughness Exercises Monday, April 1th, 2013 Practical Ideas for Your Coach ... Monday, March 25th, 2013 How To Develop The Best Youth ... Monday, March 25th, 2013 Youth Soccer Development: Ment ... Monday, March 18th, 2013 Coaching Kids to be Confident Monday, March 18th, 2013 Importance of Adversity in Pla ... Read More ª Referee Blog Friday, September 6th, 2013 Winning's not everything: How ... Monday, September 2th, 2013 Positive thinking pays off Friday, August 30th, 2013 In the right spot, at the rig ... Monday, May 20th, 2013 Love Soccer? Become a Ref! Monday, March 18th, 2013 ASK THE REF: What Action Woul ... Read More ª Field Status Robinwood Park Daniels Adams Lake Silverlake Follow Us On Bartlesville Adult Soccer League Green Country Soccer Association Green Country Member Clubs (with field maps) OKWU Men's Soccer OKWU Women's Soccer Oklahoma Soccer Association Teams Admin Pages Admin © Copyright 2015 Washington County Soccer Club All Rights Reserved"
docs = [unicode(doc1, "utf-8"),unicode(doc2, "utf-8")]

p_huber = clf_huber.predict(docs)
p_hinge = clf_hinge.predict(docs)
p_huber_w_ngram = clf_huber_w_ngram.predict(docs)
p_multinom = clf_multinom.predict(docs)
p_multinom_w_ngram =clf_multinom_w_ngram.predict(docs)


#print "huber:"
#for doc, category in zip(docs, p_huber):
#    print('%r => %s' % (doc, test_data.target_names[category]))
    
#print "hinge:"
#for doc, category in zip(docs, p_hinge):
#    print('%r => %s' % (doc, test_data.target_names[category]))
    
#print "huber w/ ngram:"
#for doc, category in zip(docs, p_huber_w_ngram):
#    print('%r => %s' % (doc, test_data.target_names[category]))

print "huber:" , p_huber
print "hinge:" , p_hinge
print "huber w/ ngram:" , p_huber_w_ngram
print "multinom:" , p_multinom
print "multinom w/ ngram:" , p_multinom_w_ngram


# In[19]:

from cassandra.cluster import Cluster

cluster = Cluster(['10.103.8.13'])
session = cluster.connect('webpage')

good = []
junk = []

rows = session.execute('SELECT * FROM p LIMIT  20000')
for row in rows:
    p_huber_w_ngram = clf_hinge.predict([unicode(row.value, "ISO-8859-1")])
    if p_huber_w_ngram == 1:
        junk.append(p_huber_w_ngram)
    if p_huber_w_ngram == 0:
        good.append(p_huber_w_ngram)
    #print p_huber_w_ngram
        print row.key


# In[18]:

print len(junk)
print len(good)


# In[ ]:



