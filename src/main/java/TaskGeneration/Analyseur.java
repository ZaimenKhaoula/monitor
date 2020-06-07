/* Generated By:JavaCC: Do not edit this line. Analyseur.java */
package TaskGeneration;

import Tasks.*;

public class Analyseur implements AnalyseurConstants {
private  int idCounter = 0;

  final public void start() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case createM:
      CreateMonitoringResource();
      break;
    case updateM:
      UpdateMonitoringResource();
      break;
    case createN:
      CreateNotifierResource();
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public UpdateMonitor UpdateMonitoringResource() throws ParseException {
  UpdateMonitor task = new UpdateMonitor();
  Token tok1=null;
  Token tok2=null;
    jj_consume_token(updateM);
    tok1 = jj_consume_token(Id);
                    task.setMetricName(tok1.toString());
    jj_consume_token(grammarkeyWords);
    jj_consume_token(sym);
    jj_consume_token(operateur);
    jj_consume_token(grammarkeyWords);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case periodic:
      tok1 = jj_consume_token(periodic);
                 task.createRate(tok1.toString());
      jj_consume_token(grammarkeyWords);
      jj_consume_token(accoladO);
      tok2 = jj_consume_token(number);
                                                                                              task.getRate().getParameters().add(tok2.toString());
      tok2 = jj_consume_token(timeUnit);
                                                                                                                                                                     task.getRate().getParameters().add(tok2.toString());
      jj_consume_token(accoladF);
      break;
    case stochastic:
      tok1 = jj_consume_token(stochastic);
                   task.createRate(tok1.toString());
      jj_consume_token(grammarkeyWords);
      jj_consume_token(accoladO);
      tok2 = jj_consume_token(loi);
                                         ((StochasticRate)(task.getRate())).setLoi( tok2.toString());
      label_1:
      while (true) {
        tok2 = jj_consume_token(number);
                                                                                                                       task.getRate().getParameters().add(tok2.toString());
        tok2 = jj_consume_token(timeUnit);
                                                                                                                                                                                              task.getRate().getParameters().add(tok2.toString());
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case number:
          ;
          break;
        default:
          jj_la1[1] = jj_gen;
          break label_1;
        }
      }
      jj_consume_token(accoladF);
      break;
    case time_serie:
      tok1 = jj_consume_token(time_serie);
                   task.createRate(tok1.toString());
      jj_consume_token(grammarkeyWords);
      jj_consume_token(accoladO);
      label_2:
      while (true) {
        tok2 = jj_consume_token(number);
                                            task.getRate().getParameters().add(tok2.toString());
        tok2 = jj_consume_token(timeUnit);
                                                                                                                   task.getRate().getParameters().add(tok2.toString());
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case number:
          ;
          break;
        default:
          jj_la1[2] = jj_gen;
          break label_2;
        }
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case grammarkeyWords:
        jj_consume_token(grammarkeyWords);
        break;
      default:
        jj_la1[3] = jj_gen;
        ;
      }
      jj_consume_token(accoladF);
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
 {if (true) return task;}
    throw new Error("Missing return statement in function");
  }

  final public CreateNotifier CreateNotifierResource() throws ParseException {
Token tok1= null;
CreateNotifier task = new CreateNotifier();
    jj_consume_token(createN);
          task.setId("notifier"+idCounter); idCounter++;
    jj_consume_token(operateur);
    tok1 = jj_consume_token(MetricName);
                                                                                       task.getExpression().add(tok1.toString());task.getMetrics().add(tok1.toString());
    label_3:
    while (true) {
      tok1 = jj_consume_token(operateur);
                    task.getExpression().add(tok1.toString());
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case number:
        tok1 = jj_consume_token(number);
                                                                                task.getExpression().add(tok1.toString());
        break;
      case MetricName:
        tok1 = jj_consume_token(MetricName);
                                                                                                                                               task.getExpression().add(tok1.toString());task.getMetrics().add(tok1.toString());
        break;
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case operateur:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_3;
      }
    }
    jj_consume_token(grammarkeyWords);
    tok1 = jj_consume_token(url);
                               task.setUrl(tok1.toString());
  {if (true) return task;}
    throw new Error("Missing return statement in function");
  }

// we can read the value of admin metric(global metric) or internal metric ,
// a get operation on internal metric enables it if it was disabled.
  final public ReadMonitor ReadMonitoringResource() throws ParseException {
Token tok1= null;
ReadMonitor task = new ReadMonitor();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MetricName:
      tok1 = jj_consume_token(MetricName);
                    task.setMetricName(tok1.toString());
      break;
    case Id:
      tok1 = jj_consume_token(Id);
          task.setInternalMetricUniqueIdentifier(tok1.toString());task.setInternalMetric(true);
  {if (true) return task;}
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public CreateMonitor CreateMonitoringResource() throws ParseException {
  Token tok1 =null;
  Token tok2 =null;
  CreateMonitor task= new CreateMonitor();
  Counter counter = new Counter();
  RTT rtt= new RTT();
    jj_consume_token(createM);
    jj_consume_token(grammarkeyWords);
    tok1 = jj_consume_token(MetricName);
                                                  task.getAdminmetric().setMetricName(tok1.toString()); task.setId(tok1.toString());
    jj_consume_token(operateur);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case number:
    case Id:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case number:
        tok1 = jj_consume_token(number);
                     task.getExpression().add(tok1.toString());
        break;
      case Id:
        tok1 = jj_consume_token(Id);
                                                                             counter.setIdMs(tok1.toString());
     task.getAdminmetric().setType("counter");
     task.getExpression().add(counter.getIdMs()+"."+counter.getMetricName()); task.getAdminmetric().getMetrics().add(counter);
        label_4:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case operateur:
            ;
            break;
          default:
            jj_la1[8] = jj_gen;
            break label_4;
          }
          tok1 = jj_consume_token(operateur);
                       task.getExpression().add(tok1.toString());
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case number:
            tok1 = jj_consume_token(number);
                                                                                  task.getExpression().add(tok1.toString());
            break;
          case Id:
            tok1 = jj_consume_token(Id);
               counter.setIdMs(tok1.toString());
     task.getExpression().add(counter.getIdMs()+"."+counter.getMetricName()); task.getAdminmetric().getMetrics().add(counter);
            break;
          default:
            jj_la1[9] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        break;
      default:
        jj_la1[10] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    case fonction:
    case RTT:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case fonction:
        tok1 = jj_consume_token(fonction);
                 System.out.println(tok1.toString()); task.getExpression().add(tok1.toString());
        tok1 = jj_consume_token(parenthO);
                                                                                                                 System.out.println(tok1.toString());task.getExpression().add(tok1.toString());
        break;
      default:
        jj_la1[11] = jj_gen;
        ;
      }
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case fonction:
          tok1 = jj_consume_token(fonction);
                  System.out.println(tok1.toString());task.getExpression().add(tok1.toString());
          tok1 = jj_consume_token(parenthO);
                                                                                                                  System.out.println(tok1.toString());task.getExpression().add(tok1.toString());
          break;
        default:
          jj_la1[12] = jj_gen;
          ;
        }
        label_6:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case number:
            tok1 = jj_consume_token(number);
                task.getExpression().add(tok1.toString());
            break;
          case RTT:
            jj_consume_token(RTT);
            jj_consume_token(separateur);
            tok1 = jj_consume_token(Idms);
                                                                                           System.out.println(tok1.toString());rtt.setIDusSource(tok1.toString()); (task.getAdminmetric()).setType("rtt");
            jj_consume_token(separateur);
            tok1 = jj_consume_token(Idms);
 System.out.println(tok1.toString());rtt.setIDusDestination(tok1.toString());
                                                                                (task.getExpression()).add(rtt.toString());
                                                                                                                             ((task.getAdminmetric()).getMetrics()).add(rtt);
            break;
          default:
            jj_la1[13] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          label_7:
          while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case operateur:
              ;
              break;
            default:
              jj_la1[14] = jj_gen;
              break label_7;
            }
            tok1 = jj_consume_token(operateur);
                  System.out.println(tok1.toString());task.getExpression().add(tok1.toString());
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case number:
              tok1 = jj_consume_token(number);
                                                                                                                 task.getExpression().add(tok1.toString());
              break;
            case RTT:
              jj_consume_token(RTT);
              jj_consume_token(separateur);
              tok1 = jj_consume_token(Idms);
                               System.out.println(tok1.toString());rtt.setIDusSource(tok1.toString());
              jj_consume_token(separateur);
              tok1 = jj_consume_token(Idms);
 System.out.println(tok1.toString());rtt.setIDusDestination(tok1.toString());
                                                                                task.getExpression().add(rtt.toString());
                                                                                                                           (task.getAdminmetric()).getMetrics().add(rtt);
              break;
            default:
              jj_la1[15] = jj_gen;
              jj_consume_token(-1);
              throw new ParseException();
            }
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case virgule:
            tok1 = jj_consume_token(virgule);
                 System.out.println(tok1.toString()); task.getExpression().add(tok1.toString());
            break;
          default:
            jj_la1[16] = jj_gen;
            ;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case RTT:
          case number:
            ;
            break;
          default:
            jj_la1[17] = jj_gen;
            break label_6;
          }
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case parenthF:
          tok1 = jj_consume_token(parenthF);
                 System.out.println(tok1.toString());task.getExpression().add(tok1.toString());System.out.println("hadi kafal bifa");
          break;
        default:
          jj_la1[18] = jj_gen;
          ;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case virgule:
          tok1 = jj_consume_token(virgule);
                 System.out.println(tok1.toString()); task.getExpression().add(tok1.toString());
          break;
        default:
          jj_la1[19] = jj_gen;
          ;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case fonction:
        case RTT:
        case number:
          ;
          break;
        default:
          jj_la1[20] = jj_gen;
          break label_5;
        }
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case parenthF:
        tok1 = jj_consume_token(parenthF);
                 System.out.println(tok1.toString());task.getExpression().add(tok1.toString());
        break;
      default:
        jj_la1[21] = jj_gen;
        ;
      }
      label_8:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case operateur:
          ;
          break;
        default:
          jj_la1[22] = jj_gen;
          break label_8;
        }
  System.out.println("dkhal l operateur tania");
        tok1 = jj_consume_token(operateur);
                                                                   task.getExpression().add(tok1.toString());
        label_9:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case number:
            tok1 = jj_consume_token(number);
                                                                                                                              task.getExpression().add(tok1.toString());
            break;
          case fonction:
          case parenthO:
          case RTT:
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case fonction:
              tok1 = jj_consume_token(fonction);
                 task.getExpression().add(tok1.toString());
              break;
            default:
              jj_la1[23] = jj_gen;
              ;
            }
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case parenthO:
              tok1 = jj_consume_token(parenthO);
                                                                               task.getExpression().add(tok1.toString());
              break;
            default:
              jj_la1[24] = jj_gen;
              ;
            }
            label_10:
            while (true) {
              switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
              case fonction:
                tok1 = jj_consume_token(fonction);
                  task.getExpression().add(tok1.toString());
                break;
              default:
                jj_la1[25] = jj_gen;
                ;
              }
              switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
              case parenthO:
                tok1 = jj_consume_token(parenthO);
                                                                                task.getExpression().add(tok1.toString());
                break;
              default:
                jj_la1[26] = jj_gen;
                ;
              }
              label_11:
              while (true) {
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case number:
                  tok1 = jj_consume_token(number);
                task.getExpression().add(tok1.toString());
                  break;
                case RTT:
                  jj_consume_token(RTT);
                  jj_consume_token(separateur);
                  tok1 = jj_consume_token(Idms);
                                                                                           rtt.setIDusSource(tok1.toString());
                  jj_consume_token(separateur);
                  tok1 = jj_consume_token(Idms);
 rtt.setIDusDestination(tok1.toString());
                                            task.getExpression().add(rtt.toString());
                                                                                       task.getAdminmetric().getMetrics().add(rtt);
                  break;
                default:
                  jj_la1[27] = jj_gen;
                  jj_consume_token(-1);
                  throw new ParseException();
                }
                label_12:
                while (true) {
                  switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                  case operateur:
                    ;
                    break;
                  default:
                    jj_la1[28] = jj_gen;
                    break label_12;
                  }
                  tok1 = jj_consume_token(operateur);
                  task.getExpression().add(tok1.toString());
                  switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                  case number:
                    tok1 = jj_consume_token(number);
                                                                             task.getExpression().add(tok1.toString());
                    break;
                  case RTT:
                    jj_consume_token(RTT);
                    jj_consume_token(separateur);
                    tok1 = jj_consume_token(Idms);
                               rtt.setIDusSource(tok1.toString());
                    jj_consume_token(separateur);
                    tok1 = jj_consume_token(Idms);
 rtt.setIDusDestination(tok1.toString());
                                            task.getExpression().add(rtt.toString());
                                                                                       task.getAdminmetric().getMetrics().add(rtt);
                    break;
                  default:
                    jj_la1[29] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
                  }
                }
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case RTT:
                case number:
                  ;
                  break;
                default:
                  jj_la1[30] = jj_gen;
                  break label_11;
                }
              }
              switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
              case parenthF:
                tok1 = jj_consume_token(parenthF);
                 task.getExpression().add(tok1.toString());
                break;
              default:
                jj_la1[31] = jj_gen;
                ;
              }
              switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
              case fonction:
              case parenthO:
              case RTT:
              case number:
                ;
                break;
              default:
                jj_la1[32] = jj_gen;
                break label_10;
              }
            }
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case virgule:
              tok1 = jj_consume_token(virgule);
                 System.out.println(tok1.toString()); task.getExpression().add(tok1.toString());
              break;
            default:
              jj_la1[33] = jj_gen;
              ;
            }
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case parenthF:
              tok1 = jj_consume_token(parenthF);
                 task.getExpression().add(tok1.toString());
              break;
            default:
              jj_la1[34] = jj_gen;
              ;
            }
            break;
          default:
            jj_la1[35] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case fonction:
          case parenthO:
          case RTT:
          case number:
            ;
            break;
          default:
            jj_la1[36] = jj_gen;
            break label_9;
          }
        }
      }
      break;
    default:
      jj_la1[37] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(separateur);
    jj_consume_token(grammarkeyWords);
    jj_consume_token(operateur);
    jj_consume_token(grammarkeyWords);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case periodic:
      tok1 = jj_consume_token(periodic);
                     task.createRate(tok1.toString());
      jj_consume_token(grammarkeyWords);
      tok2 = jj_consume_token(number);
                                                                                        System.out.println(tok2.toString());task.getRate().getParameters().add(tok2.toString());
      tok2 = jj_consume_token(timeUnit);
                                                                                                                                                                                                   System.out.println(tok2.toString());task.getRate().getParameters().add(tok2.toString());
      break;
    case stochastic:
      tok1 = jj_consume_token(stochastic);
                     task.createRate(tok1.toString());
      jj_consume_token(grammarkeyWords);
      jj_consume_token(accoladO);
      tok2 = jj_consume_token(loi);
                                         ((StochasticRate)(task.getRate())).setLoi( tok2.toString());
      label_13:
      while (true) {
        tok2 = jj_consume_token(number);
                                                                                                                       task.getRate().getParameters().add(tok2.toString());
        tok2 = jj_consume_token(timeUnit);
                                                                                                                                                                                              task.getRate().getParameters().add(tok2.toString());
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case number:
          ;
          break;
        default:
          jj_la1[38] = jj_gen;
          break label_13;
        }
      }
      jj_consume_token(accoladF);
      break;
    case time_serie:
      tok1 = jj_consume_token(time_serie);
                    task.createRate(tok1.toString());
      jj_consume_token(grammarkeyWords);
      jj_consume_token(accoladO);
      label_14:
      while (true) {
        tok2 = jj_consume_token(number);
                                            task.getRate().getParameters().add(tok2.toString());
        tok2 = jj_consume_token(timeUnit);
                                                                                                                   task.getRate().getParameters().add(tok2.toString());
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case number:
          ;
          break;
        default:
          jj_la1[39] = jj_gen;
          break label_14;
        }
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case grammarkeyWords:
        jj_consume_token(grammarkeyWords);
        break;
      default:
        jj_la1[40] = jj_gen;
        ;
      }
      jj_consume_token(accoladF);
      break;
    default:
      jj_la1[41] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
 {if (true) return task;}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public AnalyseurTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[42];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x700000,0x2000000,0x2000000,0x1000,0xe0000,0x22000000,0x20,0xa0000000,0x20,0x82000000,0x82000000,0x40,0x40,0x2008000,0x20,0x2008000,0x400,0x2008000,0x200,0x400,0x2008040,0x200,0x20,0x40,0x100,0x40,0x100,0x2008000,0x20,0x2008000,0x2008000,0x200,0x2008140,0x400,0x200,0x2008140,0x2008140,0x82008040,0x2000000,0x2000000,0x1000,0xe0000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
   }

  /** Constructor with InputStream. */
  public Analyseur(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Analyseur(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new AnalyseurTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 42; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 42; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Analyseur(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new AnalyseurTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 42; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 42; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Analyseur(AnalyseurTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 42; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(AnalyseurTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 42; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[34];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 42; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 34; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
