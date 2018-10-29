//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramaticaULTIMA.y"
 package ParserP; 
import AnalizadorLexico.ArchController;
import AnalizadorLexico.Token;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short S_MAYOR_IGUAL=257;
public final static short S_MENOR_IGUAL=258;
public final static short S_DISTINTO=259;
public final static short CTE_D=260;
public final static short CTE_USLINTEGER=261;
public final static short COMENTARIO=262;
public final static short CADENA=263;
public final static short ID=264;
public final static short IF=265;
public final static short THEN=266;
public final static short ELSE=267;
public final static short END_IF=268;
public final static short DO=269;
public final static short FUN=270;
public final static short DOUBLE=271;
public final static short USLINTEGER=272;
public final static short RETURN=273;
public final static short PRINT=274;
public final static short ASIGNACION=275;
public final static short CASE=276;
public final static short VOID=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    3,    3,    5,    4,    4,    2,
    2,    2,    8,    8,    6,    6,   11,   10,   13,   10,
   10,   14,   15,   12,   12,   16,   16,   16,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   17,   16,    9,    9,    7,    7,    7,    7,
   20,   20,   22,   22,   22,   18,   18,   24,   24,   24,
   24,   24,   24,   24,   23,   23,   25,   25,   27,   26,
   26,   28,   28,   28,   28,   19,   19,   19,   30,   19,
   31,   33,   34,   21,   21,   35,   35,   37,   32,   32,
   32,   36,   36,   36,   38,   38,   40,   38,   39,   29,
   41,   41,   41,   41,   41,   41,
};
final static short yylen[] = {                            2,
    1,    1,    3,    1,    1,    2,    0,    4,    3,    2,
    2,    1,    2,    2,    2,    1,   12,    3,    0,    2,
    1,    2,    0,    8,    1,    8,    8,    8,    8,    6,
    6,    8,    8,    7,    8,    8,    8,    8,    6,    6,
    8,    7,    0,   11,    1,    1,    1,    1,    1,    1,
    4,    1,    4,    4,    2,    7,    1,    7,    7,    7,
    7,    6,    4,    6,    2,    1,    4,    1,    4,    1,
    1,    4,    4,    4,    3,    3,    5,    2,    0,    4,
    0,    0,    0,    6,    1,    2,    2,    0,    4,    3,
    1,    3,    3,    1,    1,    1,    0,    2,    2,    3,
    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   46,   45,    0,    0,    0,    0,
    0,    1,    2,    4,    0,    0,   12,    0,   16,   47,
   48,   49,   50,   52,   57,    0,   85,    5,    0,   70,
   71,   97,    0,   95,    0,    0,    0,   94,   96,    0,
    0,    0,    0,    0,    0,   55,    0,    0,    0,    0,
    0,    0,   13,   10,   14,   11,   19,    0,   21,    0,
    0,    7,    6,   98,   99,   82,    0,    0,    0,    0,
    0,    0,   75,  104,  105,  106,  101,  102,  103,    0,
    0,    0,    0,    0,    0,    0,   66,    0,   68,    0,
    9,    3,   20,   22,    0,   79,    0,   76,    8,    0,
    0,    0,   92,   93,   73,   74,   72,    0,    0,   53,
   54,   51,    0,    0,   63,   65,    0,    0,    0,    0,
   18,   80,    0,    0,   89,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   77,   84,    0,    0,   62,   69,
   67,    0,    0,    0,   64,    0,    0,    0,    0,   25,
   58,   59,   60,   61,   56,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   40,    0,    0,    0,   39,    0,    0,    0,   31,
    0,    0,    0,   30,   17,   34,    0,    0,    0,    0,
    0,   42,    0,    0,    0,    0,    0,    0,   35,   36,
   37,   38,   41,    0,   33,   26,   27,   28,   29,   32,
   24,    0,   43,   44,
};
final static short yydgoto[] = {                         11,
   12,   28,   29,   14,   99,   15,   16,   17,   18,   58,
   19,  149,   93,   59,  241,  150,  244,   20,   21,   22,
   23,   24,   86,   25,   87,   88,   89,   26,   42,  122,
   35,   43,  100,  136,   27,   37,  125,   38,   39,   64,
   80,
};
final static short yysindex[] = {                      -115,
  322,  -31,  -40, -253,    0,    0,  -28,  -31,  -39,  322,
    0,    0,    0,    0,   12,   13,    0, -246,    0,    0,
    0,    0,    0,    0,    0,   31,    0,    0,   88,    0,
    0,    0, -233,    0, -243,  -23,   25,    0,    0,  -31,
  -31, -196,   -7,   37, -183,    0, -180,  -23, -179, -164,
 -173,  115,    0,    0,    0,    0,    0,  -55,    0,  322,
 -178,    0,    0,    0,    0,    0,  -31,  -31,  -31,  -31,
   52,  -22,    0,    0,    0,    0,    0,    0,    0,  -31,
   67,   70,   -2,   80,   15, -147,    0,  -52,    0,    2,
    0,    0,    0,    0, -128,    0, -115,    0,    0,  -31,
   25,   25,    0,    0,    0,    0,    0,  -23,   16,    0,
    0,    0,   21, -137,    0,    0, -132, -129,   22, -113,
    0,    0, -126,  -23,    0,  322, -137, -108, -115, -115,
 -137, -137, -137, -141,    0,    0,  295,  -88,    0,    0,
    0,  -54,  -46,  -59,    0,  -38,  -37, -191, -122,    0,
    0,    0,    0,    0,    0,  117,  -25,  121,  124,  -24,
  125,   46,  129,  322,    3,   51,  136,  138,  322,    7,
  -82,  322,   60,  141,   62,   44,  322,   75,   77,  155,
   82,   57,  148,  172,  322,  -83,  322,  322,  322, -170,
  185,  322,  322,  -80,  322,  322,  322,  309,   78,  -65,
  198,    0,  211,  229,   74,    0,  -17,  242,  256,    0,
  269,  282,  128,    0,    0,    0,  -11,   -6,    4,    5,
 -197,    0,   10,   26,   38,   39,   53,    1,    0,    0,
    0,    0,    0,  179,    0,    0,    0,    0,    0,    0,
    0,  204,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    6,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   14,  -20,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   17,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   18,    0,   19,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -15,  -10,    0,    0,    0,    0,    0,    8,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   20,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    6,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   61,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -13,   81,   -1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -45,    0,  -16,  521,    0,    0,   89,    0,
    0,   66,    0,    0,    0,   64,    0,   65,    0,    0,
    0,
};
final static int YYTABLESIZE=667;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
   51,  156,  158,   95,   33,  118,   33,   10,   52,  133,
   44,   47,   61,   33,  165,  170,  139,   57,  107,   67,
   91,   68,   91,   91,   91,   88,   65,   88,   88,   88,
   90,   66,   90,   90,   90,   67,  151,   68,  112,   91,
   91,   91,  120,  176,   88,   88,   88,  182,  100,   90,
   90,   90,   77,   79,   78,   54,   56,   86,  233,   73,
   87,   15,   78,   83,  159,  155,   69,   36,  128,  116,
  152,   70,  160,   48,  134,  234,   81,   96,  153,   82,
   13,  138,   83,  123,   84,  142,  143,  144,   97,   98,
   90,   85,  105,    2,    3,   30,   31,  164,  169,    4,
    5,    6,  206,    7,    8,    9,   13,  109,  115,   63,
  110,  116,   30,   31,  145,  140,  141,  116,   30,   31,
  113,  116,   30,   31,  137,  116,  116,  116,   71,   72,
  101,  102,   63,  103,  104,  121,  129,  114,  126,  130,
    1,  135,  132,  127,  131,  108,   30,   31,    2,    3,
  161,   30,   31,   10,    4,    5,    6,  162,    7,    8,
    9,  166,  174,  167,  171,  124,  189,  180,  172,  173,
  184,   30,   31,  177,  190,  191,  178,   13,  179,  197,
  198,  183,  185,  201,  187,  203,  204,  205,  199,  202,
  208,  209,  210,  211,  212,  213,  154,  192,  221,  193,
   30,   31,  215,  117,  195,   30,   31,  216,   94,   13,
   13,   36,   62,   30,   31,   40,   49,   63,  242,   30,
   31,   30,   31,   32,   50,   32,  157,   45,   30,   31,
  163,  168,   32,  106,   46,   91,   91,   91,   91,   92,
   88,   88,   88,   88,  243,   90,   90,   90,   90,   74,
   75,   76,  228,  111,   63,  222,  240,  119,  175,    0,
   63,  229,  181,  100,   63,  186,  230,   53,   55,   86,
   63,   63,   87,   15,   78,   83,  231,  232,   63,  194,
   81,   63,  235,   63,   63,   63,   60,    0,   63,   63,
    0,   63,   63,   63,    2,    3,  200,    0,  236,  188,
    4,    5,    6,    0,    7,    8,    9,    2,    3,  207,
  237,  238,  196,    4,    5,    6,    0,    7,    8,    9,
    2,    3,  217,    0,    0,  239,    4,    5,    6,  220,
    7,    8,    9,   23,    0,  218,    0,  146,    3,    0,
    0,    0,    0,  147,    5,    6,    0,    7,    8,    9,
  148,    2,    3,  219,    0,    0,    0,    4,    5,    6,
    0,    7,    8,    9,    0,    0,  223,    0,    0,    0,
   91,    0,    0,    0,    0,    0,    0,    0,    2,    3,
  224,    0,    0,  227,    4,    5,    6,    0,    7,    8,
    9,    2,    3,  225,    0,    0,    0,    4,    5,    6,
    0,    7,    8,    9,    2,    3,  226,    0,    0,    0,
    4,    5,    6,    0,    7,    8,    9,    0,    2,    3,
    0,    0,    0,    0,    4,    5,    6,    0,    7,    8,
    9,    0,    0,    0,    0,    2,    3,    0,    0,    0,
    0,    4,    5,    6,    0,    7,    8,    9,    2,    3,
    0,    0,    0,    0,    4,    5,    6,    0,    7,    8,
    9,    2,    3,    0,    0,    0,    0,    4,    5,    6,
    0,    7,    8,    9,    2,    3,    0,    0,    0,    0,
    4,    5,    6,    0,    7,    8,    9,    0,    0,    0,
    0,    0,    2,    3,    0,    0,    0,    0,    4,    5,
    6,    0,    7,    8,    9,    2,    3,    0,    0,    0,
    0,    4,    5,    6,    0,    7,    8,    9,    0,    2,
    3,    0,   34,   34,    0,    4,    5,    6,   34,    7,
    8,    9,    2,    3,    0,    0,    0,    0,    4,    5,
    6,    0,    7,    8,    9,    2,    3,    0,    0,    0,
    0,    4,    5,    6,    0,    7,    8,    9,  146,    3,
   34,   34,    0,    0,  147,    5,    6,    0,    7,    8,
    9,  148,    2,    3,    0,    0,    0,    0,    4,    5,
    6,  214,    7,    8,    9,    2,    3,   34,   34,   34,
   34,    4,    5,    6,    0,    7,    8,    9,    0,    0,
   34,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   34,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   34,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   40,   40,   59,   45,   58,   45,  123,   10,  123,
  264,   40,   26,   45,   40,   40,  125,  264,   41,   43,
   41,   45,   43,   44,   45,   41,  260,   43,   44,   45,
   41,  275,   43,   44,   45,   43,  125,   45,   41,   60,
   61,   62,   41,   41,   60,   61,   62,   41,   41,   60,
   61,   62,   60,   61,   62,   44,   44,   44,  256,  256,
   44,   44,   44,   44,  256,  125,   42,    2,  114,   86,
  125,   47,  264,    8,  120,  273,   40,  256,  125,  263,
    0,  127,  263,   97,  264,  131,  132,  133,  267,  268,
  264,  256,   41,  264,  265,  260,  261,  123,  123,  270,
  271,  272,  273,  274,  275,  276,   26,   41,  256,   29,
   41,  128,  260,  261,  256,  129,  130,  134,  260,  261,
   41,  138,  260,  261,  126,  142,  143,  144,   40,   41,
   67,   68,   52,   69,   70,  264,  269,  123,  123,  269,
  256,  268,  256,  123,  123,   80,  260,  261,  264,  265,
  273,  260,  261,  123,  270,  271,  272,   41,  274,  275,
  276,   41,  164,   40,   40,  100,  123,  169,  123,   41,
  172,  260,  261,  123,  176,  177,   41,   97,   41,  123,
  182,  264,  123,  185,  123,  187,  188,  189,   41,  273,
  192,  193,  273,  195,  196,  197,  256,  123,  125,  123,
  260,  261,  125,  256,  123,  260,  261,  273,  264,  129,
  130,  146,  125,  260,  261,  256,  256,  137,   40,  260,
  261,  260,  261,  264,  264,  264,  264,  256,  260,  261,
  256,  256,  264,  256,  263,  256,  257,  258,  259,  125,
  256,  257,  258,  259,   41,  256,  257,  258,  259,  257,
  258,  259,  125,  256,  174,  273,  256,  256,  256,   -1,
  180,  273,  256,  256,  184,  125,  273,  256,  256,  256,
  190,  191,  256,  256,  256,  256,  273,  273,  198,  125,
  275,  201,  273,  203,  204,  205,  256,   -1,  208,  209,
   -1,  211,  212,  213,  264,  265,  125,   -1,  273,  256,
  270,  271,  272,   -1,  274,  275,  276,  264,  265,  125,
  273,  273,  256,  270,  271,  272,   -1,  274,  275,  276,
  264,  265,  125,   -1,   -1,  273,  270,  271,  272,  256,
  274,  275,  276,  273,   -1,  125,   -1,  264,  265,   -1,
   -1,   -1,   -1,  270,  271,  272,   -1,  274,  275,  276,
  277,  264,  265,  125,   -1,   -1,   -1,  270,  271,  272,
   -1,  274,  275,  276,   -1,   -1,  125,   -1,   -1,   -1,
  256,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  264,  265,
  125,   -1,   -1,  256,  270,  271,  272,   -1,  274,  275,
  276,  264,  265,  125,   -1,   -1,   -1,  270,  271,  272,
   -1,  274,  275,  276,  264,  265,  125,   -1,   -1,   -1,
  270,  271,  272,   -1,  274,  275,  276,   -1,  264,  265,
   -1,   -1,   -1,   -1,  270,  271,  272,   -1,  274,  275,
  276,   -1,   -1,   -1,   -1,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,   -1,  274,  275,  276,  264,  265,
   -1,   -1,   -1,   -1,  270,  271,  272,   -1,  274,  275,
  276,  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,
   -1,  274,  275,  276,  264,  265,   -1,   -1,   -1,   -1,
  270,  271,  272,   -1,  274,  275,  276,   -1,   -1,   -1,
   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,   -1,  274,  275,  276,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,   -1,  274,  275,  276,   -1,  264,
  265,   -1,    2,    3,   -1,  270,  271,  272,    8,  274,
  275,  276,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,   -1,  274,  275,  276,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,   -1,  274,  275,  276,  264,  265,
   40,   41,   -1,   -1,  270,  271,  272,   -1,  274,  275,
  276,  277,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,  273,  274,  275,  276,  264,  265,   67,   68,   69,
   70,  270,  271,  272,   -1,  274,  275,  276,   -1,   -1,
   80,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  100,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  146,
};
}
final static short YYFINAL=11;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"S_MAYOR_IGUAL","S_MENOR_IGUAL","S_DISTINTO",
"CTE_D","CTE_USLINTEGER","COMENTARIO","CADENA","ID","IF","THEN","ELSE","END_IF",
"DO","FUN","DOUBLE","USLINTEGER","RETURN","PRINT","ASIGNACION","CASE","VOID",
};
final static String yyrule[] = {
"$accept : programa",
"programa : bloque",
"bloque : sentencia",
"bloque : '{' bloque_sentencias '}'",
"bloque : error_bloque",
"bloque_sentencias : sentencia",
"bloque_sentencias : bloque_sentencias sentencia",
"$$1 :",
"error_bloque : error bloque_sentencias '}' $$1",
"error_bloque : '{' bloque_sentencias error",
"sentencia : declaracion ','",
"sentencia : ejecucion ','",
"sentencia : error_sentencia_d",
"error_sentencia_d : declaracion error",
"error_sentencia_d : ejecucion error",
"declaracion : tipo lista_de_variables",
"declaracion : funcion",
"funcion : FUN ID '(' ')' '{' bloque_sentencias clousure RETURN '(' ID ')' '}'",
"lista_de_variables : lista_de_variables ';' ID",
"$$2 :",
"lista_de_variables : ID $$2",
"lista_de_variables : error_lista_de_variables",
"error_lista_de_variables : lista_de_variables ID",
"$$3 :",
"clousure : VOID ID '(' ')' '{' bloque_sentencias '}' $$3",
"clousure : error_clousure",
"error_clousure : VOID ID error ')' '{' bloque_sentencias '}' RETURN",
"error_clousure : VOID ID '(' error '{' bloque_sentencias '}' RETURN",
"error_clousure : VOID ID '(' ')' error bloque_sentencias '}' RETURN",
"error_clousure : VOID ID '(' ')' '{' bloque_sentencias error RETURN",
"error_clousure : VOID ID '(' ')' bloque_sentencias RETURN",
"error_clousure : VOID ID '{' bloque_sentencias '}' RETURN",
"error_clousure : VOID ID '(' ')' '{' bloque_sentencias '}' error",
"error_clousure : VOID error '(' ')' '{' bloque_sentencias '}' RETURN",
"error_clousure : ID '(' ')' '{' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID error ')' '{' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID '(' error '{' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID '(' ')' error bloque_sentencias '}' RETURN",
"error_clousure : FUN ID '(' ')' '{' bloque_sentencias error RETURN",
"error_clousure : FUN ID '(' ')' bloque_sentencias RETURN",
"error_clousure : FUN ID '{' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID '(' ')' '{' bloque_sentencias '}' error",
"error_clousure : FUN '(' ')' '{' bloque_sentencias '}' RETURN",
"$$4 :",
"error_clousure : FUN ID '(' ')' '{' bloque_sentencias '}' RETURN '(' ')' $$4",
"tipo : USLINTEGER",
"tipo : DOUBLE",
"ejecucion : control",
"ejecucion : seleccion",
"ejecucion : print",
"ejecucion : asignacion",
"print : PRINT '(' CADENA ')'",
"print : error_print",
"error_print : PRINT error CADENA ')'",
"error_print : PRINT '(' CADENA error",
"error_print : PRINT CADENA",
"control : CASE '(' ID ')' '{' lista_acciones '}'",
"control : error_control",
"error_control : CASE error ID ')' '{' lista_acciones '}'",
"error_control : CASE '(' ID error '{' lista_acciones '}'",
"error_control : CASE '(' ID ')' error lista_acciones '}'",
"error_control : CASE '(' ID ')' '{' lista_acciones error",
"error_control : CASE ID error '{' lista_acciones '}'",
"error_control : CASE ID lista_acciones error",
"error_control : CASE '(' ID ')' lista_acciones error",
"lista_acciones : lista_acciones accion",
"lista_acciones : accion",
"accion : cte ':' DO bloque",
"accion : error_accion",
"error_accion : cte error DO bloque",
"cte : CTE_D",
"cte : CTE_USLINTEGER",
"if_condicion : IF '(' condicion ')'",
"if_condicion : IF error condicion ')'",
"if_condicion : IF '(' condicion error",
"if_condicion : IF condicion error",
"seleccion : if_condicion bloque END_IF",
"seleccion : if_condicion bloque ELSE bloque END_IF",
"seleccion : if_condicion error",
"$$5 :",
"seleccion : if_condicion bloque error $$5",
"$$6 :",
"$$7 :",
"$$8 :",
"asignacion : ID $$6 ASIGNACION $$7 expresion $$8",
"asignacion : error_asignacion",
"error_asignacion : ID expresion",
"error_asignacion : ASIGNACION expresion",
"$$9 :",
"expresion : expresion '+' termino $$9",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : cte",
"factor : factor_negado",
"$$10 :",
"factor : ID $$10",
"factor_negado : '-' CTE_D",
"condicion : expresion comparador expresion",
"comparador : '<'",
"comparador : '>'",
"comparador : '='",
"comparador : S_MAYOR_IGUAL",
"comparador : S_MENOR_IGUAL",
"comparador : S_DISTINTO",
};

//#line 259 "gramaticaULTIMA.y"





private ArchController lexico;
public Parser(ArchController lexico)
{
  this.lexico= lexico;
} 

public int yylex(){
    Token token = this.lexico.getToken();
   if(token != null ){ 
    int val =token.getTipo();
    yyval = new ParserVal(token);
    return val;
}
   else return 0;
}

public void yyerror(String s){
    System.out.println("Parser: " + s);
}


//#line 540 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 36 "gramaticaULTIMA.y"
{System.out.println("se cargo una sentencia");}
break;
case 7:
//#line 48 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+" (aca va el numero de la linea)"+" Error sintactico: falta '{' ");}
break;
case 8:
//#line 49 "gramaticaULTIMA.y"
{/*  | error bloque_sentencias error  {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+" Error sintactico: falta '{'y '}' ");}*/}
break;
case 9:
//#line 50 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "en la linea "+"(aca va el numero de la linea)"+" Error sintactico: falta '}' ");}
break;
case 10:
//#line 55 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se cargo una lista de variables");}
break;
case 11:
//#line 56 "gramaticaULTIMA.y"
{}
break;
case 13:
//#line 64 "gramaticaULTIMA.y"
{ lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"Error sintactico: falta la coma");}
break;
case 14:
//#line 65 "gramaticaULTIMA.y"
{	lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"Error sintactico: falta la coma");}
break;
case 15:
//#line 72 "gramaticaULTIMA.y"
{System.out.println("llegue a esta puta declaracion");}
break;
case 17:
//#line 76 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure FUN");}
break;
case 19:
//#line 79 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego el identificador"+ID);}
break;
case 20:
//#line 79 "gramaticaULTIMA.y"
{System.out.println("lei un ID");}
break;
case 22:
//#line 83 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el ; que separa las variables");}
break;
case 23:
//#line 88 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure VOID");}
break;
case 24:
//#line 89 "gramaticaULTIMA.y"
{/* | FUN  ID '(' ')' '{' bloque_sentencias '}' RETURN '(' bloque_sentencias ')' {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure FUN");}
         | FUN  ID '(' ')' '{' bloque_sentencias '}' RETURN '(' ID ')' {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure FUN");} */}
break;
case 26:
//#line 95 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+ " (aca va el numero de la linea)"+"falta el primer parentesis");}
break;
case 27:
//#line 96 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta el segundo parentesis");}
break;
case 28:
//#line 97 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta la primer llave");}
break;
case 29:
//#line 98 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+ "(aca va el numero de la linea)"+"falta la segunda llave");}
break;
case 30:
//#line 99 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"faltan ambas llaves");}
break;
case 31:
//#line 100 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta ambos parentesis");}
break;
case 32:
//#line 101 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta return");}
break;
case 33:
//#line 102 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta identificador");}
break;
case 34:
//#line 103 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure "+  "(aca va el numero de la linea)"+"falta tipofuncion");}
break;
case 35:
//#line 104 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+ " (aca va el numero de la linea)"+"falta el primer parentesis");}
break;
case 36:
//#line 105 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta el segundo parentesis");}
break;
case 37:
//#line 106 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta la primer llave");}
break;
case 38:
//#line 107 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+ "(aca va el numero de la linea)"+"falta la segunda llave");}
break;
case 39:
//#line 108 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"faltan ambas llaves");}
break;
case 40:
//#line 109 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta ambos parentesis");}
break;
case 41:
//#line 110 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta return");}
break;
case 42:
//#line 111 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta identificador");}
break;
case 43:
//#line 112 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"faltan las sentencias del return");}
break;
case 44:
//#line 115 "gramaticaULTIMA.y"
{/*
tipofuncion : FUN 
            | VOID 
            ;
*/}
break;
case 45:
//#line 121 "gramaticaULTIMA.y"
{ System.out.println("lei un uslinteger ");}
break;
case 46:
//#line 122 "gramaticaULTIMA.y"
{ System.out.println("lei un double" );}
break;
case 51:
//#line 133 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se hizo un print");}
break;
case 53:
//#line 139 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el '(' ");}
break;
case 54:
//#line 140 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el ')'");}
break;
case 55:
//#line 141 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta los '(' ')' ");}
break;
case 56:
//#line 145 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se creo un case");}
break;
case 58:
//#line 149 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+" (aca va el numero de la linea)"+"falta parentesis inicio");}
break;
case 59:
//#line 150 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+" falta parentesis de cierre");}
break;
case 60:
//#line 151 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta llave inicio ");}
break;
case 61:
//#line 152 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+"(aca va el numero de la linea)"+"falta llave cierre");}
break;
case 62:
//#line 153 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta  parentesis  ");}
break;
case 63:
//#line 154 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta parentesis y llave");}
break;
case 64:
//#line 155 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan llaves ");}
break;
case 66:
//#line 159 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una accion");}
break;
case 69:
//#line 167 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ':' ");}
break;
case 70:
//#line 175 "gramaticaULTIMA.y"
{System.out.println("leida DOUBLE");}
break;
case 71:
//#line 176 "gramaticaULTIMA.y"
{System.out.println("Leida CTE");}
break;
case 73:
//#line 182 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el '(' de la condicion ");}
break;
case 74:
//#line 183 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ')' de la condicion ");}
break;
case 75:
//#line 184 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan los parentesis de la condicion");}
break;
case 76:
//#line 187 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una condicion IF");}
break;
case 77:
//#line 188 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una condicion IF con ELSE");}
break;
case 78:
//#line 189 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan el bloque de la condicion");}
break;
case 79:
//#line 190 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan end_if");}
break;
case 80:
//#line 198 "gramaticaULTIMA.y"
{/* 
seleccion : IF {System.out.println("cargue un if");}'(' condicion ')'{System.out.println("cargue una condicion");} bloque {System.out.println("cargue un BLOQUE1");} ELSE bloque {System.out.println("cargue un BLOQUE ELSE");}END_IF {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una condicion IF");}
         | IF {System.out.println("cargue un if");}'(' condicion ')'{System.out.println("cargue una condicion");} bloque 
	  | error_seleccion
	   ;


error_seleccion : 
                
                 
                IF  error condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el '(' de la condicion ");}
		| IF '(' condicion  error  bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ')' de la condicion ");}
		| IF  condicion  bloque_sentencias ELSE bloque_sentencias END_IF    {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan los parentesis de la condicion");}
        */;}
break;
case 81:
//#line 215 "gramaticaULTIMA.y"
{System.out.println("lei id");}
break;
case 82:
//#line 215 "gramaticaULTIMA.y"
{System.out.println("lei asig");}
break;
case 83:
//#line 215 "gramaticaULTIMA.y"
{System.out.println("lei exp");}
break;
case 84:
//#line 215 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una asignacion");System.out.println("realice una asignacion");}
break;
case 86:
//#line 220 "gramaticaULTIMA.y"
{ System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el := de la asignacion ");}
break;
case 87:
//#line 221 "gramaticaULTIMA.y"
{ System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ID de la asignacion ");}
break;
case 88:
//#line 227 "gramaticaULTIMA.y"
{System.out.println("se hizo una suma ");}
break;
case 89:
//#line 227 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una suma");}
break;
case 90:
//#line 228 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una resta");}
break;
case 91:
//#line 229 "gramaticaULTIMA.y"
{System.out.println("TERMINO a EXPR");}
break;
case 92:
//#line 232 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una multiplicacion");}
break;
case 93:
//#line 233 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una division");}
break;
case 94:
//#line 234 "gramaticaULTIMA.y"
{ System.out.println("FACTOR a TERMINO"); }
break;
case 95:
//#line 237 "gramaticaULTIMA.y"
{ System.out.println("CTE a FACTOR"); }
break;
case 97:
//#line 239 "gramaticaULTIMA.y"
{System.out.println("cargue un identificador");}
break;
case 98:
//#line 240 "gramaticaULTIMA.y"
{/*| error { System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el token ");}*/}
break;
//#line 1011 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
