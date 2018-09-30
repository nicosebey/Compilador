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
    8,    2,    6,    6,   10,   10,   10,   12,   11,   11,
   11,   11,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   14,
   13,    9,    9,    7,    7,    7,    7,   17,   17,   19,
   19,   19,   15,   15,   21,   21,   21,   21,   21,   21,
   21,   20,   20,   22,   22,   24,   23,   23,   25,   27,
   28,   29,   30,   16,   31,   33,   34,   18,   18,   35,
   35,   37,   32,   32,   32,   36,   36,   36,   38,   38,
   40,   38,   39,   41,   43,   26,   42,   42,   42,   42,
   42,   42,
};
final static short yylen[] = {                            2,
    1,    1,    3,    1,    1,    2,    0,    4,    3,    2,
    0,    3,    2,    1,    3,    1,    1,    2,    8,   11,
   11,    1,    7,    7,    7,    7,    6,    6,    7,    7,
    7,    7,    7,    7,    7,    6,    6,    7,    7,    0,
   11,    1,    1,    1,    1,    1,    1,    4,    1,    3,
    3,    2,    7,    1,    6,    6,    6,    6,    5,    3,
    5,    2,    1,    4,    1,    4,    1,    1,    0,    0,
    0,    0,    0,   13,    0,    0,    0,    6,    1,    2,
    2,    0,    4,    3,    1,    3,    3,    1,    1,    1,
    0,    2,    2,    0,    0,    5,    1,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   69,    0,   43,   42,    0,    0,    0,    0,
    0,    0,    1,    2,    4,    0,    0,    0,   14,   22,
   44,   45,   46,   47,   49,   54,   79,    5,    0,   67,
   68,   91,    0,    0,   89,    0,    0,    0,   88,   90,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   10,   11,   16,    0,   17,    7,    6,   92,    0,
   93,   76,    0,    0,    0,    0,   94,    0,    0,    0,
    0,   50,    0,    0,    0,    0,   63,    0,   65,    0,
    0,    0,    0,    0,    9,    3,   12,   18,    0,    8,
    0,    0,    0,    0,   86,   87,    0,    0,    0,    0,
    0,    0,    0,   48,    0,    0,   62,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   15,    0,    0,   83,
   70,    0,    0,    0,    0,    0,    0,    0,   59,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   78,    0,    0,   37,    0,    0,   36,    0,
    0,    0,   55,   66,   64,   56,    0,   57,   28,    0,
    0,   27,    0,    0,    0,   31,   71,  100,  101,  102,
   97,   98,   99,    0,   33,   35,    0,   34,   32,   39,
   53,   24,   26,    0,   25,   23,   30,    0,    0,    0,
   19,    0,    0,   72,    0,   40,    0,    0,   21,   41,
   20,   73,   74,
};
final static short yydgoto[] = {                         12,
   13,   28,   29,   15,   90,   16,   17,   87,   18,   55,
   19,   56,   20,  200,   21,   22,   23,   24,   25,   76,
   26,   77,   78,   79,   41,   97,  144,  188,  198,  203,
   36,   37,   92,  143,   27,   38,  120,   39,   40,   59,
   98,  174,  145,
};
final static short yysindex[] = {                      -111,
  291,   -9,    0,  -37,    0,    0,  -24,   -7,  -36,  -32,
  291,    0,    0,    0,    0,  -12,    4, -224,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    7,    0,
    0,    0,    2, -207,    0, -215,   31,   17,    0,    0,
   35,  -15,   37,   40, -181,   31,  -20, -175,   -6,   51,
   60,    0,    0,    0,  -54,    0,    0,    0,    0,  -30,
    0,    0,   -7,   -7,   -7,   -7,    0,  291,  -18,  -29,
  -28,    0,   55, -232,  -25, -232,    0,  -45,    0,   -4,
  291,   -2,  -22,  -21,    0,    0,    0,    0, -165,    0,
  291,   -7,   17,   17,    0,    0,   63,   -7,  148,  291,
  -96,  291,  291,    0, -110, -232,    0, -163, -156, -232,
  -90,  166,  291,  -82,  291,  291,    0,  180,   31,    0,
    0,   31, -158,  194,  291,   74,  208,  227,    0, -103,
 -111, -111,  -60, -232,  -56, -157,  246,  291,   93,  260,
  274, -147,    0, -111,  -51,    0, -146,  117,    0, -145,
 -144, -143,    0,    0,    0,    0,   15,    0,    0, -142,
  131,    0, -140, -138, -137,    0,    0,    0,    0,    0,
    0,    0,    0,   -7,    0,    0, -136,    0,    0,    0,
    0,    0,    0, -135,    0,    0,    0, -126,   31,  102,
    0, -111,  -41,    0,  -39,    0,  -27, -122,    0,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0, -127,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  105,    1,    0,    0,
    0,    0,    0,  -12,    0,  108,    0,    0,    0,    0,
    0,    0,    0,    0,  111,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  112,    0,    0,  128,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    6,   11,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  133,    0,
    0,  -43,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  140,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  142,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  143,    0,    0,    0,
    0,    0,    0,  153,    0,    0,    0,    0,  157,    0,
    0,    0,    0,    0, -127,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -74,  162,    9,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   33,
    0,  424,   22,    0,    0,    0,    0,    0,    0,    0,
    0,   -1,    0,    0,    0,   16,    0,   18,    0,    0,
    0,    0,    0,
};
final static int YYTABLESIZE=581;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        196,
   33,  199,   43,   48,   89,   34,   46,   50,  171,  173,
  172,   11,  109,  201,  129,   45,   95,   95,   95,   51,
   75,  153,  101,   35,   69,   70,  125,   30,   31,   35,
   33,   52,  134,   82,   83,   34,  111,   34,  114,   54,
  138,   85,   60,   85,   85,   85,   82,   53,   82,   82,
   82,   84,   61,   84,   84,   84,  154,  155,   65,   62,
   85,   85,   85,   66,  156,   82,   82,   82,  158,  167,
   84,   84,   84,   63,   67,   64,   99,   71,   93,   94,
   72,   73,   95,   96,   35,   35,   35,   35,   80,  112,
  119,   84,   91,  102,  103,  104,  122,  106,  117,  118,
  115,  116,   74,  121,  100,  131,  105,   68,  124,  126,
  127,  128,  132,   35,  146,  159,   81,  194,  110,   35,
  113,  137,  139,  140,  141,  166,  175,  178,  179,  180,
  182,   57,  185,  148,  186,  187,  190,  191,  130,  181,
  192,  193,  133,  135,    1,  202,  161,   75,   80,   30,
   31,   81,    2,    3,   13,   51,   30,   31,    4,    5,
    6,   14,    7,    8,    9,   10,  157,    2,    3,   30,
   31,   60,  189,    4,    5,    6,   77,    7,    8,    9,
   10,    2,    3,   61,   86,   58,   38,    4,    5,    6,
   58,    7,    8,    9,   10,   35,   29,   96,  150,   30,
   31,  197,    0,   30,   31,  168,  169,  170,    0,   88,
  108,    0,   58,   95,   95,   95,   35,  163,    0,    0,
   30,   31,  195,    3,   32,    0,   42,   47,    4,    5,
    6,   49,    7,    8,    9,   10,    2,    3,   44,   30,
   31,  177,    4,    5,    6,    0,    7,    8,    9,   10,
   30,   31,   30,   31,   32,  184,   32,   85,   85,   85,
   58,    0,   82,   82,   82,    0,    0,   84,   84,   84,
    2,    3,  123,   58,   30,   31,    4,    5,    6,   58,
    7,    8,    9,   10,    0,   58,    0,   58,   58,   58,
  136,    0,   14,   14,    0,    0,    0,    0,   58,    0,
   58,   58,   58,    0,  142,   14,    0,    0,    0,   58,
    0,    0,    0,    0,    0,   85,    0,    0,  147,    0,
    0,    0,   58,    2,    3,    0,    0,    0,    0,    4,
    5,    6,  151,    7,    8,    9,   10,    2,    3,    0,
    0,    0,    0,    4,    5,    6,  149,    7,    8,    9,
   10,  152,    0,   14,    0,    0,    2,    3,   58,    0,
    0,    0,    4,    5,    6,  162,    7,    8,    9,   10,
  160,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    2,    3,    0,    0,  164,    0,    4,    5,    6,  176,
    7,    8,    9,   10,    2,    3,    0,    0,  165,    0,
    4,    5,    6,  183,    7,    8,    9,   10,    0,    0,
    0,    2,    3,    0,    0,    0,    0,    4,    5,    6,
    0,    7,    8,    9,   10,    0,    0,    0,    0,    2,
    3,    0,    0,    0,    0,    4,    5,    6,    0,    7,
    8,    9,   10,    2,    3,    0,    0,    0,    0,    4,
    5,    6,    0,    7,    8,    9,   10,    2,    3,    0,
    0,    0,    0,    4,    5,    6,    0,    7,    8,    9,
   10,    2,    3,    0,    0,    0,    0,    4,    5,    6,
    0,    7,    8,    9,   10,    0,    0,    0,    0,    0,
    2,    3,    0,    0,    0,    0,    4,    5,    6,  107,
    7,    8,    9,   10,    0,    0,    0,    0,    0,    2,
    3,    0,    0,    0,    0,    4,    5,    6,    0,    7,
    8,    9,   10,    2,    3,    0,    0,    0,  107,    4,
    5,    6,    0,    7,    8,    9,   10,    2,    3,    0,
    0,    0,    0,    4,    5,    6,    0,    7,    8,    9,
   10,    0,    0,  107,    2,    3,  107,    0,  107,    0,
    4,    5,    6,    0,    7,    8,    9,   10,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  107,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   40,   41,   40,   40,   59,   45,    8,   40,   60,   61,
   62,  123,   58,   41,  125,   40,   60,   61,   62,   11,
   41,  125,   41,    2,   40,   41,  123,  260,  261,    8,
   40,   44,  123,   40,   41,   45,   41,   45,   41,  264,
  123,   41,   41,   43,   44,   45,   41,   44,   43,   44,
   45,   41,  260,   43,   44,   45,  131,  132,   42,  275,
   60,   61,   62,   47,  125,   60,   61,   62,  125,  144,
   60,   61,   62,   43,   40,   45,   68,   41,   63,   64,
   41,  263,   65,   66,   63,   64,   65,   66,  264,   81,
   92,   41,  123,  123,  123,   41,   98,  123,  264,   91,
  123,  123,  123,   41,  123,  269,   74,  123,  100,  101,
  102,  103,  269,   92,  273,  273,  123,  192,  123,   98,
  123,  113,  114,  115,  116,  273,  273,  273,  273,  273,
  273,  125,  273,  125,  273,  273,  273,  273,  106,  125,
  267,   40,  110,  111,  256,  268,  138,  275,   44,  260,
  261,   44,  264,  265,   44,   44,  260,  261,  270,  271,
  272,    0,  274,  275,  276,  277,  134,  264,  265,  260,
  261,   44,  174,  270,  271,  272,   44,  274,  275,  276,
  277,  264,  265,   44,  125,   44,   44,  270,  271,  272,
   29,  274,  275,  276,  277,  174,   44,   41,  125,  260,
  261,  193,   -1,  260,  261,  257,  258,  259,   -1,  264,
  256,   -1,   51,  257,  258,  259,  195,  125,   -1,   -1,
  260,  261,  264,  265,  264,   -1,  264,  264,  270,  271,
  272,  264,  274,  275,  276,  277,  264,  265,  263,  260,
  261,  125,  270,  271,  272,   -1,  274,  275,  276,  277,
  260,  261,  260,  261,  264,  125,  264,  257,  258,  259,
   99,   -1,  257,  258,  259,   -1,   -1,  257,  258,  259,
  264,  265,  125,  112,  260,  261,  270,  271,  272,  118,
  274,  275,  276,  277,   -1,  124,   -1,  126,  127,  128,
  125,   -1,  131,  132,   -1,   -1,   -1,   -1,  137,   -1,
  139,  140,  141,   -1,  125,  144,   -1,   -1,   -1,  148,
   -1,   -1,   -1,   -1,   -1,  256,   -1,   -1,  125,   -1,
   -1,   -1,  161,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  125,  274,  275,  276,  277,  264,  265,   -1,
   -1,   -1,   -1,  270,  271,  272,  273,  274,  275,  276,
  277,  125,   -1,  192,   -1,   -1,  264,  265,  197,   -1,
   -1,   -1,  270,  271,  272,  273,  274,  275,  276,  277,
  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  264,  265,   -1,   -1,  125,   -1,  270,  271,  272,  273,
  274,  275,  276,  277,  264,  265,   -1,   -1,  125,   -1,
  270,  271,  272,  273,  274,  275,  276,  277,   -1,   -1,
   -1,  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,
   -1,  274,  275,  276,  277,   -1,   -1,   -1,   -1,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,   -1,  274,
  275,  276,  277,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,   -1,  274,  275,  276,  277,  264,  265,   -1,
   -1,   -1,   -1,  270,  271,  272,   -1,  274,  275,  276,
  277,  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,
   -1,  274,  275,  276,  277,   -1,   -1,   -1,   -1,   -1,
  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,   76,
  274,  275,  276,  277,   -1,   -1,   -1,   -1,   -1,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,   -1,  274,
  275,  276,  277,  264,  265,   -1,   -1,   -1,  105,  270,
  271,  272,   -1,  274,  275,  276,  277,  264,  265,   -1,
   -1,   -1,   -1,  270,  271,  272,   -1,  274,  275,  276,
  277,   -1,   -1,  130,  264,  265,  133,   -1,  135,   -1,
  270,  271,  272,   -1,  274,  275,  276,  277,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  157,
};
}
final static short YYFINAL=12;
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
"$$2 :",
"sentencia : ejecucion ',' $$2",
"declaracion : tipo lista_de_variables",
"declaracion : clousure",
"lista_de_variables : lista_de_variables ';' ID",
"lista_de_variables : ID",
"lista_de_variables : error_lista_de_variables",
"error_lista_de_variables : lista_de_variables ID",
"clousure : VOID ID '(' ')' '{' bloque_sentencias '}' RETURN",
"clousure : FUN ID '(' ')' '{' bloque_sentencias '}' RETURN '(' bloque_sentencias ')'",
"clousure : FUN ID '(' ')' '{' bloque_sentencias '}' RETURN '(' ID ')'",
"clousure : error_clousure",
"error_clousure : VOID ID ')' '{' bloque_sentencias '}' RETURN",
"error_clousure : VOID ID '(' '{' bloque_sentencias '}' RETURN",
"error_clousure : VOID ID '(' ')' bloque_sentencias '}' RETURN",
"error_clousure : VOID ID '(' ')' '{' bloque_sentencias RETURN",
"error_clousure : VOID ID '(' ')' bloque_sentencias RETURN",
"error_clousure : VOID ID '{' bloque_sentencias '}' RETURN",
"error_clousure : VOID ID '(' ')' '{' bloque_sentencias '}'",
"error_clousure : VOID '(' ')' '{' bloque_sentencias '}' RETURN",
"error_clousure : ID '(' ')' '{' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID ')' '{' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID '(' '{' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID '(' ')' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID '(' ')' '{' bloque_sentencias RETURN",
"error_clousure : FUN ID '(' ')' bloque_sentencias RETURN",
"error_clousure : FUN ID '{' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID '(' ')' '{' bloque_sentencias '}'",
"error_clousure : FUN '(' ')' '{' bloque_sentencias '}' RETURN",
"$$3 :",
"error_clousure : FUN ID '(' ')' '{' bloque_sentencias '}' RETURN '(' ')' $$3",
"tipo : USLINTEGER",
"tipo : DOUBLE",
"ejecucion : control",
"ejecucion : seleccion",
"ejecucion : print",
"ejecucion : asignacion",
"print : PRINT '(' CADENA ')'",
"print : error_print",
"error_print : PRINT CADENA ')'",
"error_print : PRINT '(' CADENA",
"error_print : PRINT CADENA",
"control : CASE '(' ID ')' '{' lista_acciones '}'",
"control : error_control",
"error_control : CASE ID ')' '{' lista_acciones '}'",
"error_control : CASE '(' ID '{' lista_acciones '}'",
"error_control : CASE '(' ID ')' lista_acciones '}'",
"error_control : CASE '(' ID ')' '{' lista_acciones",
"error_control : CASE ID '{' lista_acciones '}'",
"error_control : CASE ID lista_acciones",
"error_control : CASE '(' ID ')' lista_acciones",
"lista_acciones : lista_acciones accion",
"lista_acciones : accion",
"accion : cte ':' DO bloque",
"accion : error_accion",
"error_accion : cte error DO bloque",
"cte : CTE_D",
"cte : CTE_USLINTEGER",
"$$4 :",
"$$5 :",
"$$6 :",
"$$7 :",
"$$8 :",
"seleccion : IF $$4 '(' condicion ')' $$5 bloque $$6 ELSE bloque $$7 END_IF $$8",
"$$9 :",
"$$10 :",
"$$11 :",
"asignacion : ID $$9 ASIGNACION $$10 expresion $$11",
"asignacion : error_asignacion",
"error_asignacion : ID expresion",
"error_asignacion : ASIGNACION expresion",
"$$12 :",
"expresion : expresion '+' termino $$12",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : cte",
"factor : factor_negado",
"$$13 :",
"factor : ID $$13",
"factor_negado : '-' CTE_D",
"$$14 :",
"$$15 :",
"condicion : $$14 expresion $$15 comparador expresion",
"comparador : '<'",
"comparador : '>'",
"comparador : '='",
"comparador : S_MAYOR_IGUAL",
"comparador : S_MENOR_IGUAL",
"comparador : S_DISTINTO",
};

//#line 236 "gramaticaULTIMA.y"





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


//#line 508 "Parser.java"
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
{System.out.println("se cargo una declaracion");}
break;
case 11:
//#line 56 "gramaticaULTIMA.y"
{}
break;
case 12:
//#line 58 "gramaticaULTIMA.y"
{/*  | error_sentencia_d 
            ;




error_sentencia_d : declaracion error { lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"Error sintactico: falta la coma");}
		   | ejecucion error {	lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"Error sintactico: falta la coma");}
                */}
break;
case 16:
//#line 78 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego el identificador"+ID);}
break;
case 18:
//#line 82 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el ; que separa las variables");}
break;
case 19:
//#line 87 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure VOID");}
break;
case 20:
//#line 88 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure FUN");}
break;
case 21:
//#line 89 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure FUN");}
break;
case 23:
//#line 94 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+ " (aca va el numero de la linea)"+"falta el primer parentesis");}
break;
case 24:
//#line 95 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta el segundo parentesis");}
break;
case 25:
//#line 96 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta la primer llave");}
break;
case 26:
//#line 97 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+ "(aca va el numero de la linea)"+"falta la segunda llave");}
break;
case 27:
//#line 98 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"faltan ambas llaves");}
break;
case 28:
//#line 99 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta ambos parentesis");}
break;
case 29:
//#line 100 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta return");}
break;
case 30:
//#line 101 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta identificador");}
break;
case 31:
//#line 102 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure "+  "(aca va el numero de la linea)"+"falta tipofuncion");}
break;
case 32:
//#line 103 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+ " (aca va el numero de la linea)"+"falta el primer parentesis");}
break;
case 33:
//#line 104 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta el segundo parentesis");}
break;
case 34:
//#line 105 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta la primer llave");}
break;
case 35:
//#line 106 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+ "(aca va el numero de la linea)"+"falta la segunda llave");}
break;
case 36:
//#line 107 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"faltan ambas llaves");}
break;
case 37:
//#line 108 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta ambos parentesis");}
break;
case 38:
//#line 109 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta return");}
break;
case 39:
//#line 110 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta identificador");}
break;
case 40:
//#line 111 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"faltan las sentencias del return");}
break;
case 41:
//#line 114 "gramaticaULTIMA.y"
{/*
tipofuncion : FUN 
            | VOID 
            ;
*/}
break;
case 48:
//#line 132 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se hizo un print");}
break;
case 50:
//#line 138 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el '(' ");}
break;
case 51:
//#line 139 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el ')'");}
break;
case 52:
//#line 140 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta los '(' ')' ");}
break;
case 53:
//#line 144 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se creo un case");}
break;
case 55:
//#line 148 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+" (aca va el numero de la linea)"+"falta algun parentesis o llave");}
break;
case 56:
//#line 149 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+" falta algun parentesis o llave");}
break;
case 57:
//#line 150 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
break;
case 58:
//#line 151 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+"(aca va el numero de la linea)"+"falta algun parentesis o llave");}
break;
case 59:
//#line 152 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
break;
case 60:
//#line 153 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave");}
break;
case 61:
//#line 154 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
break;
case 63:
//#line 158 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una accion");}
break;
case 66:
//#line 166 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ':' ");}
break;
case 67:
//#line 174 "gramaticaULTIMA.y"
{System.out.println("leida DOUBLE");}
break;
case 68:
//#line 175 "gramaticaULTIMA.y"
{System.out.println("Leida CTE");}
break;
case 69:
//#line 179 "gramaticaULTIMA.y"
{System.out.println("cargue un if");}
break;
case 70:
//#line 179 "gramaticaULTIMA.y"
{System.out.println("cargue una condicion");}
break;
case 71:
//#line 179 "gramaticaULTIMA.y"
{System.out.println("cargue un BLOQUE1");}
break;
case 72:
//#line 179 "gramaticaULTIMA.y"
{System.out.println("cargue un BLOQUE ELSE");}
break;
case 73:
//#line 179 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una condicion IF");}
break;
case 74:
//#line 180 "gramaticaULTIMA.y"
{/*	  | error_seleccion
	   ;

{/*
error_seleccion : 
                 IF '('condicion')' {System.out.println("LOGRE ENTRAR ACA PERO PUTO");} error_bloque ELSE bloque END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el BLOQUE de la condicion ");}
                 
                IF  condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el '(' de la condicion ");}
		| IF '(' condicion   bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ')' de la condicion ");}
		| IF  condicion  bloque_sentencias ELSE bloque_sentencias END_IF    {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan los parentesis de la condicion");}
             */;}
break;
case 75:
//#line 192 "gramaticaULTIMA.y"
{System.out.println("lei id");}
break;
case 76:
//#line 192 "gramaticaULTIMA.y"
{System.out.println("lei asig");}
break;
case 77:
//#line 192 "gramaticaULTIMA.y"
{System.out.println("lei exp");}
break;
case 78:
//#line 192 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una asignacion");System.out.println("realice una asignacion");}
break;
case 80:
//#line 197 "gramaticaULTIMA.y"
{ System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el := de la asignacion ");}
break;
case 81:
//#line 198 "gramaticaULTIMA.y"
{ System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ID de la asignacion ");}
break;
case 82:
//#line 204 "gramaticaULTIMA.y"
{System.out.println("se hizo una suma ");}
break;
case 83:
//#line 204 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una suma");}
break;
case 84:
//#line 205 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una resta");}
break;
case 85:
//#line 206 "gramaticaULTIMA.y"
{System.out.println("TERMINO a EXPR");}
break;
case 86:
//#line 209 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una multiplicacion");}
break;
case 87:
//#line 210 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una division");}
break;
case 88:
//#line 211 "gramaticaULTIMA.y"
{ System.out.println("FACTOR a TERMINO"); }
break;
case 89:
//#line 214 "gramaticaULTIMA.y"
{ System.out.println("CTE a FACTOR"); }
break;
case 91:
//#line 216 "gramaticaULTIMA.y"
{System.out.println("cargue un identificador");}
break;
case 92:
//#line 217 "gramaticaULTIMA.y"
{/*| error { System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el token ");}*/}
break;
case 94:
//#line 224 "gramaticaULTIMA.y"
{System.out.println("llegue aca pero no lee exp");}
break;
case 95:
//#line 224 "gramaticaULTIMA.y"
{System.out.println("encontre un puta expresion");}
break;
case 97:
//#line 227 "gramaticaULTIMA.y"
{System.out.println("cargue un menor");}
break;
//#line 967 "Parser.java"
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
