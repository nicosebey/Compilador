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
    0,    1,    1,    1,    3,    3,    4,    4,    4,    2,
    2,    2,    7,    7,    5,    5,    9,    9,    9,   11,
   10,   10,   10,   10,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   13,   12,    8,    8,    6,    6,    6,    6,   16,
   16,   18,   18,   18,   14,   14,   20,   20,   20,   20,
   20,   20,   20,   19,   19,   21,   21,   23,   22,   22,
   24,   26,   27,   28,   29,   15,   30,   32,   33,   17,
   17,   34,   34,   36,   31,   31,   31,   35,   35,   35,
   37,   37,   39,   37,   38,   40,   42,   25,   41,   41,
   41,   41,   41,   41,
};
final static short yylen[] = {                            2,
    1,    1,    3,    1,    1,    2,    3,    3,    3,    2,
    2,    1,    2,    2,    2,    1,    3,    1,    1,    2,
    8,   11,   11,    1,    7,    7,    7,    7,    6,    6,
    7,    7,    7,    7,    7,    7,    7,    6,    6,    7,
    7,    0,   11,    1,    1,    1,    1,    1,    1,    4,
    1,    3,    3,    2,    7,    1,    6,    6,    6,    6,
    5,    3,    5,    2,    1,    4,    1,    4,    1,    1,
    0,    0,    0,    0,    0,   13,    0,    0,    0,    6,
    1,    2,    2,    0,    4,    3,    1,    3,    3,    1,
    1,    1,    0,    2,    2,    0,    0,    5,    1,    1,
    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   71,    0,   45,   44,    0,    0,    0,    0,
    0,    0,    1,    2,    4,    0,    0,   12,    0,   16,
   24,   46,   47,   48,   49,   51,   56,   81,    5,    0,
   69,   70,   93,    0,    0,   91,    0,    0,    0,   90,
   92,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   13,   10,   14,   11,   18,    0,   19,    8,
    7,    6,   94,    0,   95,   78,    0,    0,    0,    0,
   96,    0,    0,    0,    0,   52,    0,    0,    0,    0,
   65,    0,   67,    0,    0,    0,    0,    0,    9,    3,
   20,    0,    0,    0,    0,    0,   88,   89,    0,    0,
    0,    0,    0,    0,    0,   50,    0,    0,   64,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   17,    0,
    0,   85,   72,    0,    0,    0,    0,    0,    0,    0,
   61,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   80,    0,    0,   39,    0,    0,
   38,    0,    0,    0,   57,   68,   66,   58,    0,   59,
   30,    0,    0,   29,    0,    0,    0,   33,   73,  102,
  103,  104,   99,  100,  101,    0,   35,   37,    0,   36,
   34,   41,   55,   26,   28,    0,   27,   25,   32,    0,
    0,    0,   21,    0,    0,   74,    0,   42,    0,    0,
   23,   43,   22,   75,   76,
};
final static short yydgoto[] = {                         12,
   13,   29,   30,   15,   16,   17,   18,   19,   58,   20,
   59,   21,  202,   22,   23,   24,   25,   26,   80,   27,
   81,   82,   83,   42,   99,  146,  190,  200,  205,   37,
   38,   94,  145,   28,   39,  122,   40,   41,   63,  100,
  176,  147,
};
final static short yysindex[] = {                        50,
 -181,   -9,    0,  -37,    0,    0,  -24,   -7,  -36,  -32,
 -181,    0,    0,    0,    0,  -14,  -10,    0, -215,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   92,
    0,    0,    0,    3, -206,    0, -194,   37,   -5,    0,
    0,   52,  -13,   56,   60, -157,   37,  -20, -155,   -8,
   79,  131,    0,    0,    0,    0,    0,  -54,    0,    0,
    0,    0,    0,   -1,    0,    0,   -7,   -7,   -7,   -7,
    0, -181,  -18,    1,   10,    0,   82, -220,   11, -220,
    0,  -45,    0,  -12, -181,   -2,   13,   15,    0,    0,
    0, -139, -181,   -7,   -5,   -5,    0,    0,   85,   -7,
  220, -181,  -88, -181, -181,    0, -110, -220,    0, -142,
 -141, -220, -116,  234, -181,   67, -181, -181,    0,  248,
   37,    0,    0,   37, -138,  263, -181,  149,  279,  293,
    0, -105,   50,   50, -103, -220, -101, -134,  309, -181,
  168,  323,  338, -130,    0,   50,  -51,    0, -127,  185,
    0, -126, -124, -121,    0,    0,    0,    0,  -99,    0,
    0, -120,  203,    0, -108, -102,  -98,    0,    0,    0,
    0,    0,    0,    0,    0,   -7,    0,    0,  -95,    0,
    0,    0,    0,    0,    0,  -94,    0,    0,    0, -104,
   37,  132,    0,   50,  -41,    0,  -39,    0,  -27,  -87,
    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  -90,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   16,    2,    0,
    0,    0,    0,    0,   17,    0,   21,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   22,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   26,    0,    0,   27,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    7,   12,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   31,    0,    0,  -43,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   32,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   33,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   34,    0,
    0,    0,    0,    0,    0,   35,    0,    0,    0,    0,
  133,    0,    0,    0,    0,    0,  -90,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -46,  166,   14,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  239,    0,
    5,  100,    0,    0,    0,    0,    0,    0,    0,    0,
    4,    0,    0,    0,   46,    0,  -11,    0,    0,    0,
    0,    0,
};
final static int YYTABLESIZE=615;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        198,
   34,  201,   44,   49,   92,   35,  136,   51,  173,  175,
  174,   47,  111,  203,  131,   46,   97,   97,   97,  155,
   79,  158,  103,  160,   52,  183,   73,   74,  113,   54,
   34,   86,   87,   56,  127,   35,   69,   35,  116,   31,
   32,   70,   87,   64,   87,   87,   87,   84,   57,   84,
   84,   84,   86,   65,   86,   86,   86,   97,   98,   82,
   54,   87,   87,   87,   83,   15,   84,   84,   84,   53,
   62,   86,   86,   86,   79,   63,   60,   40,   31,   67,
   66,   68,    2,    3,  109,  101,  156,  157,    4,    5,
    6,   71,    7,    8,    9,   10,   75,  121,  114,  169,
   76,   36,   78,  124,  102,   77,  120,   36,   84,   72,
  112,  109,   95,   96,   85,  126,  128,  129,  130,   88,
  115,   93,  106,  104,  119,  123,  133,  134,  139,  141,
  142,  143,  105,  108,  148,  117,  109,  118,  161,  109,
  150,  109,  168,   31,   32,  177,  180,  196,  181,   31,
   32,  182,  184,  163,   31,   32,   31,   32,   31,   32,
   31,   32,  194,  109,  187,   14,   36,   36,   36,   36,
  188,  195,   11,   98,  189,    2,    3,  192,  193,  191,
  204,    4,    5,    6,   77,    7,    8,    9,   10,  140,
    0,    0,    0,   36,    0,   62,    0,    0,    0,   36,
    0,    0,    0,    0,    0,  170,  171,  172,  199,   91,
  110,    0,    0,   97,   97,   97,   61,   62,    0,    0,
   31,   32,  197,    3,   33,    0,   43,   48,    4,    5,
    6,   50,    7,    8,    9,   10,    2,    3,   45,   31,
   32,   53,    4,    5,    6,   55,    7,    8,    9,   10,
   31,   32,   31,   32,   33,   90,   33,   87,   87,   87,
   87,    0,   84,   84,   84,   84,   62,   86,   86,   86,
   86,   82,   54,  152,    0,   36,   83,   15,    0,   62,
    0,   53,   62,    0,    0,   62,   79,   63,   60,   40,
   31,   62,  165,   62,   62,   62,   36,    0,   14,   14,
    0,    0,    0,    0,   62,    1,   62,   62,   62,  179,
    0,   14,    0,    2,    3,   62,  107,    0,    0,    4,
    5,    6,    0,    7,    8,    9,   10,  186,   62,    0,
    2,    3,    0,    0,    0,    0,    4,    5,    6,    0,
    7,    8,    9,   10,  125,    0,  132,   60,    0,    0,
  135,  137,    0,    0,    0,    2,    3,    0,  138,   14,
    0,    4,    5,    6,   62,    7,    8,    9,   10,    0,
    0,    0,  144,    0,  159,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   89,  149,    0,    0,
    0,    0,    0,    0,    2,    3,    0,    0,    0,    0,
    4,    5,    6,  153,    7,    8,    9,   10,    0,    0,
    0,    0,    2,    3,    0,    0,    0,  154,    4,    5,
    6,  151,    7,    8,    9,   10,    0,    0,    0,    0,
    0,    2,    3,  162,    0,    0,    0,    4,    5,    6,
  164,    7,    8,    9,   10,    0,    0,  166,    2,    3,
    0,    0,    0,    0,    4,    5,    6,  178,    7,    8,
    9,   10,  167,    0,    0,    0,    2,    3,    0,    0,
    0,    0,    4,    5,    6,  185,    7,    8,    9,   10,
    0,    0,    0,    2,    3,    0,    0,    0,    0,    4,
    5,    6,    0,    7,    8,    9,   10,    2,    3,    0,
    0,    0,    0,    4,    5,    6,    0,    7,    8,    9,
   10,    2,    3,    0,    0,    0,    0,    4,    5,    6,
    0,    7,    8,    9,   10,    0,    2,    3,    0,    0,
    0,    0,    4,    5,    6,    0,    7,    8,    9,   10,
    0,    0,    2,    3,    0,    0,    0,    0,    4,    5,
    6,    0,    7,    8,    9,   10,    2,    3,    0,    0,
    0,    0,    4,    5,    6,    0,    7,    8,    9,   10,
    0,    0,    2,    3,    0,    0,    0,    0,    4,    5,
    6,    0,    7,    8,    9,   10,    2,    3,    0,    0,
    0,    0,    4,    5,    6,    0,    7,    8,    9,   10,
    0,    2,    3,    0,    0,    0,    0,    4,    5,    6,
    0,    7,    8,    9,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   40,   41,   40,   40,   59,   45,  123,   40,   60,   61,
   62,    8,   58,   41,  125,   40,   60,   61,   62,  125,
   41,  125,   41,  125,   11,  125,   40,   41,   41,   44,
   40,   40,   41,   44,  123,   45,   42,   45,   41,  260,
  261,   47,   41,   41,   43,   44,   45,   41,  264,   43,
   44,   45,   41,  260,   43,   44,   45,   69,   70,   44,
   44,   60,   61,   62,   44,   44,   60,   61,   62,   44,
   44,   60,   61,   62,   44,   44,   44,   44,   44,   43,
  275,   45,  264,  265,   80,   72,  133,  134,  270,  271,
  272,   40,  274,  275,  276,  277,   41,   94,   85,  146,
   41,    2,  123,  100,  123,  263,   93,    8,  264,  123,
  123,  107,   67,   68,  123,  102,  103,  104,  105,   41,
  123,  123,   41,  123,  264,   41,  269,  269,  115,  116,
  117,  118,  123,  123,  273,  123,  132,  123,  273,  135,
  127,  137,  273,  260,  261,  273,  273,  194,  273,  260,
  261,  273,  273,  140,  260,  261,  260,  261,  260,  261,
  260,  261,  267,  159,  273,    0,   67,   68,   69,   70,
  273,   40,  123,   41,  273,  264,  265,  273,  273,  176,
  268,  270,  271,  272,  275,  274,  275,  276,  277,  123,
   -1,   -1,   -1,   94,   -1,   30,   -1,   -1,   -1,  100,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  195,  264,
  256,   -1,   -1,  257,  258,  259,  125,   52,   -1,   -1,
  260,  261,  264,  265,  264,   -1,  264,  264,  270,  271,
  272,  264,  274,  275,  276,  277,  264,  265,  263,  260,
  261,  256,  270,  271,  272,  256,  274,  275,  276,  277,
  260,  261,  260,  261,  264,  125,  264,  256,  257,  258,
  259,   -1,  256,  257,  258,  259,  101,  256,  257,  258,
  259,  256,  256,  125,   -1,  176,  256,  256,   -1,  114,
   -1,  256,  256,   -1,   -1,  120,  256,  256,  256,  256,
  256,  126,  125,  128,  129,  130,  197,   -1,  133,  134,
   -1,   -1,   -1,   -1,  139,  256,  141,  142,  143,  125,
   -1,  146,   -1,  264,  265,  150,   78,   -1,   -1,  270,
  271,  272,   -1,  274,  275,  276,  277,  125,  163,   -1,
  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,   -1,
  274,  275,  276,  277,  125,   -1,  108,  256,   -1,   -1,
  112,  113,   -1,   -1,   -1,  264,  265,   -1,  125,  194,
   -1,  270,  271,  272,  199,  274,  275,  276,  277,   -1,
   -1,   -1,  125,   -1,  136,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  256,  125,   -1,   -1,
   -1,   -1,   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,
  270,  271,  272,  125,  274,  275,  276,  277,   -1,   -1,
   -1,   -1,  264,  265,   -1,   -1,   -1,  125,  270,  271,
  272,  273,  274,  275,  276,  277,   -1,   -1,   -1,   -1,
   -1,  264,  265,  125,   -1,   -1,   -1,  270,  271,  272,
  273,  274,  275,  276,  277,   -1,   -1,  125,  264,  265,
   -1,   -1,   -1,   -1,  270,  271,  272,  273,  274,  275,
  276,  277,  125,   -1,   -1,   -1,  264,  265,   -1,   -1,
   -1,   -1,  270,  271,  272,  273,  274,  275,  276,  277,
   -1,   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,   -1,  274,  275,  276,  277,  264,  265,   -1,
   -1,   -1,   -1,  270,  271,  272,   -1,  274,  275,  276,
  277,  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,
   -1,  274,  275,  276,  277,   -1,  264,  265,   -1,   -1,
   -1,   -1,  270,  271,  272,   -1,  274,  275,  276,  277,
   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,   -1,  274,  275,  276,  277,  264,  265,   -1,   -1,
   -1,   -1,  270,  271,  272,   -1,  274,  275,  276,  277,
   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,   -1,  274,  275,  276,  277,  264,  265,   -1,   -1,
   -1,   -1,  270,  271,  272,   -1,  274,  275,  276,  277,
   -1,  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,
   -1,  274,  275,  276,  277,
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
"error_bloque : error bloque_sentencias '}'",
"error_bloque : error bloque_sentencias error",
"error_bloque : '{' bloque_sentencias error",
"sentencia : declaracion ','",
"sentencia : ejecucion ','",
"sentencia : error_sentencia_d",
"error_sentencia_d : declaracion error",
"error_sentencia_d : ejecucion error",
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
"$$1 :",
"error_clousure : FUN ID '(' ')' '{' bloque_sentencias '}' RETURN '(' ')' $$1",
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
"$$2 :",
"$$3 :",
"$$4 :",
"$$5 :",
"$$6 :",
"seleccion : IF $$2 '(' condicion ')' $$3 bloque $$4 ELSE bloque $$5 END_IF $$6",
"$$7 :",
"$$8 :",
"$$9 :",
"asignacion : ID $$7 ASIGNACION $$8 expresion $$9",
"asignacion : error_asignacion",
"error_asignacion : ID expresion",
"error_asignacion : ASIGNACION expresion",
"$$10 :",
"expresion : expresion '+' termino $$10",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : cte",
"factor : factor_negado",
"$$11 :",
"factor : ID $$11",
"factor_negado : '-' CTE_D",
"$$12 :",
"$$13 :",
"condicion : $$12 expresion $$13 comparador expresion",
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


//#line 516 "Parser.java"
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
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+" Error sintactico: falta '{'y '}' ");}
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
case 13:
//#line 64 "gramaticaULTIMA.y"
{ lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"Error sintactico: falta la coma");}
break;
case 14:
//#line 65 "gramaticaULTIMA.y"
{	lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"Error sintactico: falta la coma");}
break;
case 18:
//#line 78 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego el identificador"+ID);}
break;
case 20:
//#line 82 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el ; que separa las variables");}
break;
case 21:
//#line 87 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure VOID");}
break;
case 22:
//#line 88 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure FUN");}
break;
case 23:
//#line 89 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure FUN");}
break;
case 25:
//#line 94 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+ " (aca va el numero de la linea)"+"falta el primer parentesis");}
break;
case 26:
//#line 95 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta el segundo parentesis");}
break;
case 27:
//#line 96 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta la primer llave");}
break;
case 28:
//#line 97 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+ "(aca va el numero de la linea)"+"falta la segunda llave");}
break;
case 29:
//#line 98 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"faltan ambas llaves");}
break;
case 30:
//#line 99 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta ambos parentesis");}
break;
case 31:
//#line 100 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta return");}
break;
case 32:
//#line 101 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta identificador");}
break;
case 33:
//#line 102 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure "+  "(aca va el numero de la linea)"+"falta tipofuncion");}
break;
case 34:
//#line 103 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+ " (aca va el numero de la linea)"+"falta el primer parentesis");}
break;
case 35:
//#line 104 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta el segundo parentesis");}
break;
case 36:
//#line 105 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta la primer llave");}
break;
case 37:
//#line 106 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+ "(aca va el numero de la linea)"+"falta la segunda llave");}
break;
case 38:
//#line 107 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"faltan ambas llaves");}
break;
case 39:
//#line 108 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta ambos parentesis");}
break;
case 40:
//#line 109 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta return");}
break;
case 41:
//#line 110 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta identificador");}
break;
case 42:
//#line 111 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"faltan las sentencias del return");}
break;
case 43:
//#line 114 "gramaticaULTIMA.y"
{/*
tipofuncion : FUN 
            | VOID 
            ;
*/}
break;
case 50:
//#line 132 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se hizo un print");}
break;
case 52:
//#line 138 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el '(' ");}
break;
case 53:
//#line 139 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el ')'");}
break;
case 54:
//#line 140 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta los '(' ')' ");}
break;
case 57:
//#line 148 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+" (aca va el numero de la linea)"+"falta algun parentesis o llave");}
break;
case 58:
//#line 149 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+" falta algun parentesis o llave");}
break;
case 59:
//#line 150 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
break;
case 60:
//#line 151 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+"(aca va el numero de la linea)"+"falta algun parentesis o llave");}
break;
case 61:
//#line 152 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
break;
case 62:
//#line 153 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave");}
break;
case 63:
//#line 154 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
break;
case 65:
//#line 158 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una accion");}
break;
case 68:
//#line 166 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ':' ");}
break;
case 69:
//#line 174 "gramaticaULTIMA.y"
{System.out.println("leida DOUBLE");}
break;
case 70:
//#line 175 "gramaticaULTIMA.y"
{System.out.println("Leida CTE");}
break;
case 71:
//#line 179 "gramaticaULTIMA.y"
{System.out.println("cargue un if");}
break;
case 72:
//#line 179 "gramaticaULTIMA.y"
{System.out.println("cargue una condicion");}
break;
case 73:
//#line 179 "gramaticaULTIMA.y"
{System.out.println("cargue un BLOQUE1");}
break;
case 74:
//#line 179 "gramaticaULTIMA.y"
{System.out.println("cargue un BLOQUE ELSE");}
break;
case 75:
//#line 179 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una seleccion");}
break;
case 76:
//#line 180 "gramaticaULTIMA.y"
{/*| error_seleccion
	   ;


error_seleccion : 
                 IF '('condicion')' {System.out.println("LOGRE ENTRAR ACA PERO PUTO");} error_bloque ELSE bloque END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el BLOQUE de la condicion ");}
                 
                IF  condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el '(' de la condicion ");}
		| IF '(' condicion   bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ')' de la condicion ");}
		| IF  condicion  bloque_sentencias ELSE bloque_sentencias END_IF    {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan los parentesis de la condicion");}
               */}
break;
case 77:
//#line 192 "gramaticaULTIMA.y"
{System.out.println("lei id");}
break;
case 78:
//#line 192 "gramaticaULTIMA.y"
{System.out.println("lei asig");}
break;
case 79:
//#line 192 "gramaticaULTIMA.y"
{System.out.println("lei exp");}
break;
case 80:
//#line 192 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una asignacion");System.out.println("realice una asignacion");}
break;
case 82:
//#line 197 "gramaticaULTIMA.y"
{ System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el := de la asignacion ");}
break;
case 83:
//#line 198 "gramaticaULTIMA.y"
{ System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ID de la asignacion ");}
break;
case 84:
//#line 204 "gramaticaULTIMA.y"
{System.out.println("se hizo una suma ");}
break;
case 85:
//#line 204 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una suma");}
break;
case 86:
//#line 205 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una resta");}
break;
case 87:
//#line 206 "gramaticaULTIMA.y"
{System.out.println("TERMINO a EXPR");}
break;
case 88:
//#line 209 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una multiplicacion");}
break;
case 89:
//#line 210 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una division");}
break;
case 90:
//#line 211 "gramaticaULTIMA.y"
{ System.out.println("FACTOR a TERMINO"); }
break;
case 91:
//#line 214 "gramaticaULTIMA.y"
{ System.out.println("CTE a FACTOR"); }
break;
case 93:
//#line 216 "gramaticaULTIMA.y"
{System.out.println("cargue un identificador");}
break;
case 94:
//#line 217 "gramaticaULTIMA.y"
{/*| error { System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el token ");}*/}
break;
case 96:
//#line 224 "gramaticaULTIMA.y"
{System.out.println("llegue aca pero no lee exp");}
break;
case 97:
//#line 224 "gramaticaULTIMA.y"
{System.out.println("encontre un puta expresion");}
break;
case 99:
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
